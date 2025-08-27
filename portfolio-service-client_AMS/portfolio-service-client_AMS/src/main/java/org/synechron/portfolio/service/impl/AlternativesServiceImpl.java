package org.synechron.portfolio.service.impl;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.entity.AlternativesEntity;
import org.synechron.esg.model.AlternativeCacheKey;
import org.synechron.esg.model.AlternativeResponse;
import org.synechron.esg.model.AlternativesCompany;
import org.synechron.esg.model.AlternativesObject;
import org.synechron.esg.model.ApplicationComponentSettings;
import org.synechron.esg.model.Company;
import org.synechron.esg.model.CompanyESGScore;
import org.synechron.esg.model.InvestableUniverseFilter;
import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.application_settings.service.ApplicationComponentSettingsService;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.enums.PortfolioTypeEnum;
import org.synechron.portfolio.request.AlternativeRequest;
import org.synechron.portfolio.response.dto.RestResponse;
import org.synechron.portfolio.response.dto.builder.AlternativesCompanyBuilder;
import org.synechron.portfolio.service.AlternativesService;
import org.synechron.portfolio.service.CalculationService;
import org.synechron.portfolio.service.InvestSuitProxy;
import org.synechron.portfolio.utils.PortfolioUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.collect.ImmutableSet;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Service
public class AlternativesServiceImpl implements AlternativesService {


    private static DecimalFormat df = new DecimalFormat("##.00");

    private static final Logger log = LoggerFactory.getLogger(AlternativesServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private DataLakeServiceProxy dataLakeProxy;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private InvestSuitProxy investSuitProxy;

    @Autowired
    private AlternativeServiceProxy alternativeServiceProxy;
    private List<AlternativesEntity> alternativesObjects;
    
    @Autowired
    private ApplicationComponentSettingsService applicationComponentSettingsService;


    @Override
    public AlternativeResponse getAlternatives(String isin, String portfolioId, String esgTargetType) {

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
        Portfolio portfolio = dataStore.get(portfolioId);

        AlternativeResponse response = null;
        List<String> isinList = new ArrayList<>();
        isinList.add(isin);

        IMap<Integer, AlternativesObject> alternativesDataStore = hazelcastInstance.getMap("alternatives");

        AlternativeRequest request = new AlternativeRequest();
        request.setEsgTargetType(esgTargetType);
        request.setIsin(isinList);
        request.setPortfolioId(portfolioId);

        Map<String, Double> currentPortfolio = portfolio.getCompanies().stream().collect(
                Collectors.toMap(Company::getIsin, Company::getHoldingValue));

        //Sort object
        currentPortfolio = currentPortfolio.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

        AlternativeCacheKey cacheKey = new AlternativeCacheKey(isinList,
                portfolio.getInvestableUniverseFilter(), portfolio.getInvestableUniverseType(),
                currentPortfolio, esgTargetType);

        if (alternativesDataStore.get(cacheKey.hashCode()) == null) {

            try {
                response = alternativeServiceProxy.getAlternative(request).getBody();
                if (response != null && response.getStatusCode() != 500 && response.getAlternatives() != null) {
                    log.info("putting in cache {} , portfolio id {}",isinList,portfolioId);
                    alternativesDataStore.put(cacheKey.hashCode(), new AlternativesObject(response, cacheKey, cacheKey.hashCode(), new Date()));
                }

            } catch (Exception ex) {
                log.error("Exception in Getting alternative : {}", ex.getStackTrace());
                response = new AlternativeResponse(null, null, null, HttpStatus.INTERNAL_SERVER_ERROR, 500);
            }

        } else {
            log.info("getting from cache {} , portfolio id {}",isinList,portfolioId);
            response = alternativesDataStore.get(cacheKey.hashCode()).getAlternativeResponse();
        }
        return response;
    }


    @Override
    public AlternativeResponse getAlternative(List<String> isin, String portfolioId, String esgTargetType) throws Exception {

        log.debug("Alternatives call started for portfolio {}, isin {}, type {}", portfolioId, isin, esgTargetType);
        AlternativeResponse response = new AlternativeResponse();
        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        if(applicationComponentSettingsMap.get(Constant.IS_SIMULATION_ENABLED).getValue().equals(Boolean.TRUE)) {

            IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
            Portfolio portfolio = dataStore.get(portfolioId);

            IMap<Integer, AlternativesObject> alternativesDataStore = hazelcastInstance.getMap("alternatives");

            AlternativeRequest request = new AlternativeRequest();
            request.setEsgTargetType(esgTargetType);
            request.setIsin(isin);
            request.setPortfolioId(portfolioId);

            Map<String, Double> currentPortfolio = portfolio.getCompanies().stream().collect(
                    Collectors.toMap(Company::getIsin, Company::getHoldingValue));

            //TODO: Logic needs to be determined for hashcode() map object
            //Sort objects
            currentPortfolio = currentPortfolio.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

            AlternativeCacheKey cacheKey = new AlternativeCacheKey(isin,
                    portfolio.getInvestableUniverseFilter(), portfolio.getInvestableUniverseType(),
                    currentPortfolio, esgTargetType);

            try {
                log.info("Size of the alternative cache : " + alternativesDataStore.values().size());

                //Fetch data from cache or invest suit
                AlternativesObject altObj = alternativesDataStore.get(cacheKey.hashCode());
                if(altObj == null && isInvestSuitEnabled()){
                    log.info("Fetching alternative response data from investsuit.");
                    response = alternativeServiceProxy.getAlternative(request).getBody();
                    if (response.getAlternatives() != null && response.getHistoricalPerformance() != null && response.getSimulatedPortfolio() != null)
                        alternativesDataStore.put(cacheKey.hashCode(), new AlternativesObject(response, cacheKey, cacheKey.hashCode(), new Date()));
                }else if(altObj != null){
                    log.info("Fetching alternative response data from cache.");
                    response = altObj.getAlternativeResponse();
                }
            } catch (Exception ex) {
                log.error("Exception in Getting alternative : {}", ex.getStackTrace());
                response = new AlternativeResponse(null, null, null, HttpStatus.INTERNAL_SERVER_ERROR, 500);
            }
        }

        return response;
    }


    @Override
    public List<AlternativesCompany> getAllCompaniesForAlternatives(String portfolioId) {
        // TODO Auto-generated method stub
        RestResponse response = new RestResponse(HttpStatus.OK, "OK");

        ResponseEntity<List<CompanyESGScore>> dataLakeresponse = null;
        List<AlternativesCompany> alternativeCompanies = null;
        IMap<String, AlternativesCompany> dataStore = hazelcastInstance.getMap("alternativeComapnies");

        IMap<String, Portfolio> portfolioData = hazelcastInstance.getMap("portfolios");

        Portfolio portfolio = portfolioData.get(portfolioId);
        InvestableUniverseFilter filter = portfolio.getInvestableUniverseFilter();

        if (dataStore != null && dataStore.size() > 0) {
            alternativeCompanies = (List<AlternativesCompany>) dataStore.values();
             response.setData(alternativeCompanies);
        } else {
            // dataStore.put(finalPortfolio.getPortfolioId(), finalPortfolio);
            try {
                dataLakeresponse = dataLakeProxy.getAllInvestibleCompanies();
                log.debug("Response :{}", dataLakeresponse.toString());
                log.debug("Response status:{}", dataLakeresponse.getStatusCode());
                log.debug("Response :{}", dataLakeresponse.getHeaders());
                log.debug("Response :{}", dataLakeresponse.getBody());
                if (dataLakeresponse.getStatusCode().equals(HttpStatus.OK) && !dataLakeresponse.getBody().isEmpty()) {
                    List<CompanyESGScore> allInvestibleUniverseCompanies = (List<CompanyESGScore>) dataLakeresponse.getBody();

                    alternativeCompanies = allInvestibleUniverseCompanies.stream().filter(a -> !a.getIsinType().equalsIgnoreCase(PortfolioTypeEnum.FUND.getValue()))
                            .map(c -> new AlternativesCompanyBuilder()
                                    .withISIN(c.getIsin())
                                    .withCompanyName(c.getInstrumentName())
                                    .withESGScore(c.getEsgScore())
                                    .withESGCombinedScore(c.getEsgCombinedScore())
                                    .withEnvScore(c.getEnvironmentPillarScore())
                                    .withSocialScore(c.getSocialPillarScore())
                                    .withGovScore(c.getGovernancePillarScore())
                                    .withSustainalyticsTotalEsgScore(c.getSustainalyticsTotalEsgScore())
                                    .withSustainalyticsEnvScore(c.getSustainalyticsEnvironmentScore())
                                    .withSustainalyticsSocialScore(c.getSustainalyticsSocialScore())
                                    .withSustainalyticsGovScore(c.getSustainalyticsGovernanceScore())
                                    .withCountry(c.getCountry())
                                    .withCountryName(c.getCountryName())
                                    .withSector(c.getSectors())
                                    .withIsinType(c.getIsinType())
                                    .build()
                            ).collect(Collectors.toList());


                    if (alternativeCompanies != null && alternativeCompanies.size() > 0) {
                        for (AlternativesCompany alternativeCompany : alternativeCompanies) {
                            dataStore.put(alternativeCompany.getIsin(), alternativeCompany);
                        }
                    }

                     response.setData(alternativeCompanies);
                }

            } catch (Exception e) {
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setError("Error Occured while acessing the Data Lake Api");
                response.setMessage("Fetching Alternatives Companies  Failed");
                response.setData(null);
                return alternativeCompanies;
            }

        }

        /*AlternativeRequestTransformService service = investSuitRequestTransformFactoryImpl.getAlternativeTransformServiceImpl(portfolio.getInvestableUniverseType());
        alternativeCompanies = service.getInvestibleUniverse(alternativeCompanies, filter);
*/
        //Mark the companies which belong to the portfolio as TRUE
        List<String> isinList = PortfolioUtils.getISINList(portfolio);
        for (AlternativesCompany altComp : alternativeCompanies) {
            if (isinList.contains(altComp.getIsin())) {
                altComp.setIsPortfolioCompany(Boolean.TRUE);
            } else {
                altComp.setIsPortfolioCompany(Boolean.FALSE);
            }
        }

        return alternativeCompanies;

    }

    @Async
    @Override
    public void cacheAlternatives(String portfolioId) throws Exception {

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
        Portfolio portfolio = dataStore.get(portfolioId);
        List<Company> companies = portfolio.getCompanies();
        Map<String, Double> currentPortfolio = portfolio.getCompanies().stream().collect(
        Collectors.toMap(Company::getIsin, Company::getHoldingValue));

        ArrayList<String> targetType = new ArrayList<>();
        targetType.add("ESG");
        targetType.add("E");
        targetType.add("S");
        targetType.add("G");

        IMap<Integer, AlternativesObject> dataStore2 = hazelcastInstance.getMap("alternatives");
        for (String targetTypeValue : targetType) {

            Set<Set<String>> allCombinations = getCombinationByTargetType(companies, targetTypeValue, portfolio.getInvestableUniverseType());
            log.info("All Combination Size : " + allCombinations.size());

            for (Set<String> list : allCombinations) {

                List<String> combination= new ArrayList<>(list);
                System.out.println(combination);
                AlternativeResponse resp = this.getAlternative(combination, portfolioId, targetTypeValue);
                if (!(resp.getAlternatives() == null) && !(resp.getHistoricalPerformance() == null) && !(resp.getSimulatedPortfolio() == null || resp.getSimulatedPortfolio().getEsgCombinedScore() == null)) {

                    System.out.println("Investsuite Alternative response " + resp);
                    log.info("Response cached for --> " + list);
                    AlternativesObject obj = new AlternativesObject();
                    obj.setAlternativeResponse(resp);

//                    List<AlternativesCompany> inv = this.getAllCompaniesForAlternatives(portfolioId);
//                    inv = inv.stream().filter(a -> !a.getIsinType().equalsIgnoreCase(PortfolioTypeEnum.FUND.getValue())).collect(Collectors.toList());
//                    List<String> investibleUniverse = inv.stream().map(AlternativesCompany::getIsin).collect(Collectors.toList());

                    InvestableUniverseFilter  filter = new InvestableUniverseFilter(
                            portfolio.getInvestableUniverseFilter().getEsg(),
                            portfolio.getInvestableUniverseFilter().getEnvironmental(),
                            portfolio.getInvestableUniverseFilter().getSocial(),
                            portfolio.getInvestableUniverseFilter().getGovernance(),
                            portfolio.getInvestableUniverseFilter().getSelectedCountries(),
                            portfolio.getInvestableUniverseFilter().getSelectedSectors(),
                            portfolio.getInvestableUniverseFilter().getSusEsg(),
                            portfolio.getInvestableUniverseFilter().getSusEnvironmental(),
                            portfolio.getInvestableUniverseFilter().getSusSocial(),
                            portfolio.getInvestableUniverseFilter().getSusGovernance(),
                            portfolio.getInvestableUniverseFilter().getFundEsgScore()
                    );

                    //Sort the object
                    currentPortfolio = currentPortfolio.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

                    AlternativeCacheKey alternativeCacheKey = new AlternativeCacheKey(combination, filter, portfolio.getInvestableUniverseType(), currentPortfolio, targetTypeValue);
                    dataStore2.put(alternativeCacheKey.hashCode(), new AlternativesObject(resp, alternativeCacheKey, alternativeCacheKey.hashCode(), new Date()));
                } else {
                    log.info("No Alternatives fetched for the given combination of isin " + list + "for the portfolio " + portfolioId);
                }
            }


        }

    }

    private Set<Set<String>> getCombinationByTargetType(List<Company> companies, String targetType, String investableUniverseType) {

        List<Company> sortedCompany = getSortedList(companies, targetType, investableUniverseType);

        List<Company> firstNElementsList = sortedCompany.stream().limit(8).collect(Collectors.toList());

        List<String> companiesISIN = firstNElementsList.stream().map(Company::getIsin).collect(Collectors.toList());
        Set<Set<String>> allCombinations = new HashSet<>();
        for (int loop = 5; loop > 0; loop--) {
            allCombinations.addAll(com.google.common.collect.Sets.combinations(ImmutableSet.copyOf(companiesISIN), loop));
        }
        log.debug(" Total combinations count for Target type -> '"+ targetType+"' and Investable universe -> '"+investableUniverseType+"' is : " + allCombinations.size());
        return allCombinations;

    }

    private List<Company> getSortedList(List<Company> companies, String esgTargetType, String investableUniverseType) {

        List<Company> sortedCompany = null;

        switch (esgTargetType) {

            case "ESG":
                if(investableUniverseType.equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)) {
                    sortedCompany = companies.stream().sorted(Comparator.comparing(Company::getInfluenceESGCombinedScore)).collect(Collectors.toList());
                }else if(investableUniverseType.equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE))  {
                    sortedCompany = companies.stream().sorted(Comparator.comparing(Company::getInfluenceESGScoreForSustainalytics)).collect(Collectors.toList());
                }else {
                    log.error("Invalid data source.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
                }
                break;

            case "E":
                if(investableUniverseType.equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)) {
                    sortedCompany = companies.stream().sorted(Comparator.comparing(Company::getInfluenceEnvironmentalScore)).collect(Collectors.toList());
                }else if(investableUniverseType.equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE))  {
                    sortedCompany = companies.stream().sorted(Comparator.comparing(Company::getInfluenceEnvironmentalScoreForSustainalytics)).collect(Collectors.toList());
                }else {
                    log.error("Invalid data source.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
                }
                break;

            case "S":
                if(investableUniverseType.equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)) {
                    sortedCompany = companies.stream().sorted(Comparator.comparing(Company::getInfluenceSocialScore)).collect(Collectors.toList());
                }else if(investableUniverseType.equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE))  {
                    sortedCompany = companies.stream().sorted(Comparator.comparing(Company::getInfluenceSocialScoreForSustainalytics)).collect(Collectors.toList());
                }else {
                    log.error("Invalid data source.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
                }
                break;

            case "G":
                if(investableUniverseType.equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)) {
                    sortedCompany = companies.stream().sorted(Comparator.comparing(Company::getInfluenceGovernanceScore)).collect(Collectors.toList());
                }else if(investableUniverseType.equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE))  {
                    sortedCompany = companies.stream().sorted(Comparator.comparing(Company::getInfluenceGovernanceScoreForSustainalytics)).collect(Collectors.toList());
                }else {
                    log.error("Invalid data source.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
                }
                break;
        }
        return sortedCompany;
    }


    @Override
    public AlternativeResponse getAlternativeFromCache(List<String> isin, String portfolioId, String esgTargetType) {
        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
        Portfolio portfolio = dataStore.get(portfolioId);
        AlternativeResponse response = null;

        Map<String, Double> currentPortfolio = portfolio.getCompanies().stream().collect(
                Collectors.toMap(Company::getIsin, Company::getHoldingValue));
        List<AlternativesCompany> inv = this.getAllCompaniesForAlternatives(portfolioId);
        inv = inv.stream().filter(a -> !a.getIsinType().equalsIgnoreCase(PortfolioTypeEnum.FUND.getValue())).collect(Collectors.toList());
        List<String> investibleUniverse = inv.stream().map(AlternativesCompany::getIsin).collect(Collectors.toList());

        InvestableUniverseFilter  filter = new InvestableUniverseFilter(
                portfolio.getInvestableUniverseFilter().getEsg(),
                portfolio.getInvestableUniverseFilter().getEnvironmental(),
                portfolio.getInvestableUniverseFilter().getSocial(),
                portfolio.getInvestableUniverseFilter().getGovernance(),
                portfolio.getInvestableUniverseFilter().getSelectedCountries(),
                portfolio.getInvestableUniverseFilter().getSelectedSectors(),
                portfolio.getInvestableUniverseFilter().getSusEsg(),
                portfolio.getInvestableUniverseFilter().getSusEnvironmental(),
                portfolio.getInvestableUniverseFilter().getSusSocial(),
                portfolio.getInvestableUniverseFilter().getSusGovernance(),
                portfolio.getInvestableUniverseFilter().getFundEsgScore()
        );


        //Sort object
        currentPortfolio = currentPortfolio.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

        AlternativeCacheKey alternativeCacheKey = new AlternativeCacheKey(isin, filter, portfolio.getInvestableUniverseType(), currentPortfolio, esgTargetType);
        System.out.println("hashcocde generated for the request " + alternativeCacheKey.hashCode());
        IMap<Integer, AlternativesObject> alternativesDataStore = hazelcastInstance.getMap("alternatives");

        AlternativesObject alternatives = alternativesDataStore.get(alternativeCacheKey.hashCode());
        if (alternatives != null && alternatives.getAlternativeResponse() != null)
            response = alternatives.getAlternativeResponse();

        return response;


    }

    @Override
    public List<AlternativesObject> retrieveCachedAlternatives() {

        IMap<Integer, AlternativesObject> alternativesDataStore = hazelcastInstance.getMap("alternatives");

        if (alternativesDataStore != null && !alternativesDataStore.isEmpty())
            return (List<AlternativesObject>) alternativesDataStore.values();
        else
            return null;
    }

    @Override
    public void evictCache() {
        IMap<Integer, AlternativesObject> alternativesDataStore = hazelcastInstance.getMap("alternatives");
        log.info("Cached records before eviction : " + alternativesDataStore.values().size());
        alternativesDataStore.evictAll();
        log.info("Cached records after eviction : " + alternativesDataStore.values().size());
    }

    @Async
    @Override
    public void loadCachedResponses() throws Exception {

        IMap<Integer, AlternativesObject> alternativesDataStore = hazelcastInstance.getMap("alternatives");

        AlternativesEntity alternativesEntity = new AlternativesEntity();
        int numberOfRecords = 0;

        try(JsonReader jsonReader = new JsonReader(
                new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("alternative.json"), StandardCharsets.UTF_8))) {
            Gson gson = new GsonBuilder().create();
            jsonReader.beginArray();

            log.info("Storing json response in cache started.");
            int count = 0;
            //Iterate the json
            while (jsonReader.hasNext()){
                alternativesEntity = gson.fromJson(jsonReader, AlternativesEntity.class);

                alternativesDataStore.put(alternativesEntity.get_id(), new AlternativesObject(alternativesEntity.getAlternativeResponse(), alternativesEntity.getAlternativeCacheKey(), alternativesEntity.get_id(), new Date()));
                log.info("No of total records : " + numberOfRecords + ", Count : " + count);
                numberOfRecords++;
                count++;

                if(count == 100){
                    alternativesDataStore.evictAll();
                    count = 0;
                }
            }
            log.info("Storing json response in cache completed.");
            jsonReader.endArray();
            System.out.println("Total records found in the json file : "+ numberOfRecords);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Async
    public void persistAlternativeToCache(List<String> isinCombination, String portfolioId, AlternativeResponse resp, String esgTargetType) {
        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
        Portfolio portfolio = dataStore.get(portfolioId);
        List<AlternativesCompany> inv = this.getAllCompaniesForAlternatives(portfolioId);
        inv = inv.stream().filter(a -> !a.getIsinType().equalsIgnoreCase(PortfolioTypeEnum.FUND.getValue())).collect(Collectors.toList());
        List<String> investibleUniverse = inv.stream().map(AlternativesCompany::getIsin).collect(Collectors.toList());
        Map<String, Double> currentPortfolio = portfolio.getCompanies().stream().collect(
                Collectors.toMap(Company::getIsin, Company::getHoldingValue));
        IMap<Integer, AlternativesObject> alternativesDataStore = hazelcastInstance.getMap("alternatives");
        InvestableUniverseFilter  filter = new InvestableUniverseFilter(
                portfolio.getInvestableUniverseFilter().getEsg(),
                portfolio.getInvestableUniverseFilter().getEnvironmental(),
                portfolio.getInvestableUniverseFilter().getSocial(),
                portfolio.getInvestableUniverseFilter().getGovernance(),
                portfolio.getInvestableUniverseFilter().getSelectedCountries(),
                portfolio.getInvestableUniverseFilter().getSelectedSectors(),
                portfolio.getInvestableUniverseFilter().getSusEsg(),
                portfolio.getInvestableUniverseFilter().getSusEnvironmental(),
                portfolio.getInvestableUniverseFilter().getSusSocial(),
                portfolio.getInvestableUniverseFilter().getSusGovernance(),
                portfolio.getInvestableUniverseFilter().getFundEsgScore()
                );

        //Sort object
        currentPortfolio = currentPortfolio.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

        AlternativeCacheKey alternativeCacheKey = new AlternativeCacheKey(isinCombination, filter, portfolio.getInvestableUniverseType(), currentPortfolio, esgTargetType);
        alternativesDataStore.put(alternativeCacheKey.hashCode(), new AlternativesObject(resp, alternativeCacheKey, alternativeCacheKey.hashCode(), new Date()));
    }

    /*@Async
    private void storeInvestSuiteRequestAndResponse(FailedInvestSuiteRequests request) {
        // TODO Auto-generated method stub

        IMap<Integer, FailedInvestSuiteRequests> dataStore = hazelcastInstance.getMap("failedAlterntaivesRequests");
        dataStore.put(request.getFailedInvestSuiteRequestsId(), request);
    }
*/


    @Override
    public void removeFromCache(String portfolioId) {
        IMap<String, AlternativeResponse> dataStore = hazelcastInstance.getMap("investableUniverse");

        //dataStore.entrySet();
        //for (Map.Entry entry : dataStore.entrySet()) {
         //   String[] key = entry.getKey().toString().split("_");
         //   if (portfolioId.equals(key[0])) {
                dataStore.remove(portfolioId);
         //   }
        //}
    }
    
    private  List<AlternativesEntity> readAlternativesCachedResponsesJson() throws IOException {

        List<AlternativesEntity> list = new ArrayList<>();
        try(JsonReader jsonReader = new JsonReader(
                new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("alternative.json"), StandardCharsets.UTF_8))) {
            Gson gson = new GsonBuilder().create();
            jsonReader.beginArray();
            int numberOfRecords = 0;
            while (jsonReader.hasNext()){
                AlternativesEntity alternativesEntity = gson.fromJson(jsonReader, AlternativesEntity.class);
                list.add(alternativesEntity);
                numberOfRecords++;
            }
            jsonReader.endArray();
            System.out.println("Total records found in the json file : "+ numberOfRecords);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
	}
    
    private boolean isInvestSuitEnabled() {
    	Map<String, ApplicationComponentSettings> applicationComponentSettingsMap = null;
		try {
			applicationComponentSettingsMap = applicationComponentSettingsService.getInitialApplicationComponentSettings();
			if(applicationComponentSettingsMap.get(Constant.IS_INVEST_SUIT_ENABLED).getValue().equals(Boolean.TRUE)) {
				return true;
	    	  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	  return false;
    	
    }
}