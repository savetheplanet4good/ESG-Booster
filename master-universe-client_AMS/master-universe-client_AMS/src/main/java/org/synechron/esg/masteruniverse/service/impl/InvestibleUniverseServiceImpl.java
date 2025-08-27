package org.synechron.esg.masteruniverse.service.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.masteruniverse.enums.PortfolioTypeEnum;
import org.synechron.esg.masteruniverse.dto.CompanyESGScore;
import org.synechron.esg.model.InvestableUniverseFilter;
import org.synechron.esg.model.Portfolio;
import org.synechron.esg.masteruniverse.dto.builder.InvestableUniverseCompanyBuilder;
import org.synechron.esg.masteruniverse.request.UpdateInvestmentUniverseFilterRequest;
import org.synechron.esg.model.AlternativesCompany;
import org.synechron.esg.masteruniverse.response.InvestibleUniverseFilterDto;
import org.synechron.esg.masteruniverse.response.InvestibleUniverseResponse;
import org.synechron.esg.masteruniverse.response.UpdateInvestibleUniverseResponse;
import org.synechron.esg.masteruniverse.service.InvestibleUniverseService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestibleUniverseServiceImpl implements InvestibleUniverseService {

    private static String PORTFOLIOS = "portfolios";

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private DataLakeServiceProxy dataLakeServiceProxy;

    private static final Logger log = LoggerFactory.getLogger(InvestibleUniverseServiceImpl.class);

    @Override
    public InvestibleUniverseResponse getInvestibleUniverse(Portfolio portfolio) {

        log.info("Portfolio : " , portfolio.toString());
        List<AlternativesCompany> alternativesCompanies = getCompanyResponse(portfolio);
        log.info("Investable Universe Companies : " + alternativesCompanies.toString());
        InvestableUniverseFilter investibleUniverse = portfolio.getInvestableUniverseFilter();
        log.info("Investable Universe filter : " + investibleUniverse.toString());
        List<String> investableUniverseCountries = investibleUniverse.getSelectedCountries();
        List<String> investableUniverseSectors = investibleUniverse.getSelectedSectors();

        return new InvestibleUniverseResponse(alternativesCompanies,
                new InvestibleUniverseFilterDto(investibleUniverse.getEsg(),
                        investibleUniverse.getEnvironmental(), investibleUniverse.getSocial(),
                        investibleUniverse.getGovernance(), investableUniverseCountries,
                        investableUniverseSectors,
                        investibleUniverse.getSusEsg(), investibleUniverse.getSusEnvironmental(), investibleUniverse.getSusSocial(), investibleUniverse.getSusGovernance(),
                        investibleUniverse.getFundEsgScore())
        );
    }

    @Override
    public InvestibleUniverseResponse getInvestibleUniverse(String portfolioId) {
        log.info("Portfolio ID : " + portfolioId);
        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
        Portfolio portfolio = dataStore.get(portfolioId);
        log.info("Portfolio fetched from cache. Portfolio : " + portfolio.toString());
        List<AlternativesCompany> alternativesCompanies = getCompanyResponse(portfolio);
        log.info("Investable Universe Companies : " + alternativesCompanies.toString());
        InvestableUniverseFilter investibleUniverse = portfolio.getInvestableUniverseFilter();
        log.info("Investable Universe filter : " + investibleUniverse.toString());
        List<String> investableUniverseCountries = investibleUniverse.getSelectedCountries();
        List<String> investableUniverseSectors = investibleUniverse.getSelectedSectors();

        return new InvestibleUniverseResponse(alternativesCompanies, new InvestibleUniverseFilterDto(investibleUniverse.getEsg(), investibleUniverse.getEnvironmental(), investibleUniverse.getSocial(), investibleUniverse.getGovernance(), investableUniverseCountries, investableUniverseSectors,
                investibleUniverse.getSusEsg(), investibleUniverse.getSusEnvironmental(), investibleUniverse.getSusSocial(), investibleUniverse.getSusGovernance(),
                investibleUniverse.getFundEsgScore()));
    }

    @Override
    public UpdateInvestibleUniverseResponse updateInvestibleFilter(UpdateInvestmentUniverseFilterRequest request, String portfolioId) {
        log.info("Investable Universe Request : " + request.toString() + "\n Portfolio ID : " + portfolioId);
        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
        Portfolio portfolio = dataStore.get(portfolioId);
        log.info("Portfolio fetched from cache. Portfolio : " + portfolio.toString());
        //InvestableUniverseFilter filter = new InvestableUniverseFilter(request.getEsg(), request.getEnvironmental(), request.getSocial(), request.getGovernance(), request.getSelectedCountries(), request.getSelectedSectors());

        InvestableUniverseFilter filter = portfolio.getInvestableUniverseFilter();
        if(portfolio.getInvestableUniverseType().equalsIgnoreCase("REFINITIV")){
            filter.setEsg(request.getEsg());
            filter.setEnvironmental(request.getEnvironmental());
            filter.setSocial(request.getSocial());
            filter.setGovernance(request.getGovernance());
            filter.setSelectedSectors(request.getSelectedSectors());
            filter.setSelectedCountries(request.getSelectedCountries());
        } else if(portfolio.getInvestableUniverseType().equalsIgnoreCase("SUSTAINALYTICS")){
            filter.setSusEsg(request.getEsg());
            filter.setSusEnvironmental(request.getEnvironmental());
            filter.setSusSocial(request.getSocial());
            filter.setSusGovernance(request.getGovernance());
            filter.setSelectedSectors(request.getSelectedSectors());
            filter.setSelectedCountries(request.getSelectedCountries());
        }else if(portfolio.getInvestableUniverseType().equalsIgnoreCase("MSCI")){
            filter.setFundEsgScore(request.getEsg());
        }else{
            log.error("Invalid data source.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
        }

        log.info("Filter : " + filter);
        portfolio.setInvestableUniverseFilter(filter);
        log.info("Save portfolio in cache. Portfolio : " + portfolio.toString());
        dataStore.put(portfolioId, portfolio);

        return new UpdateInvestibleUniverseResponse("SUCCESS", "Record Updated Successfully");
    }

    public List<AlternativesCompany> getCompanyResponse(Portfolio portfolio) {

        log.info("Portfolio : " + portfolio.toString());
        List<AlternativesCompany> alternativeCompanies = null;
        ResponseEntity<List<CompanyESGScore>> dataLakeresponse = null;
        IMap<String, AlternativesCompany> dataStore = hazelcastInstance.getMap("alternativeComapnies");

        if (dataStore != null && dataStore.size() > 0) {
            log.info("Companies fetched from cache. Companies : " + dataStore.values().toString());
            alternativeCompanies = (List<AlternativesCompany>) dataStore.values();
        } else {
            try {
                dataLakeresponse = dataLakeServiceProxy.getAllInvestibleCompanies();
                log.debug("Response :{}", dataLakeresponse.toString());
                log.debug("Response status:{}", dataLakeresponse.getStatusCode());
                log.debug("Response :{}", dataLakeresponse.getHeaders());
                log.debug("Response :{}", dataLakeresponse.getBody());
                if (dataLakeresponse.getStatusCode().equals(HttpStatus.OK) && !dataLakeresponse.getBody().isEmpty()) {
                    List<CompanyESGScore> allInvestibleUniverseCompanies = dataLakeresponse.getBody();
                        alternativeCompanies = allInvestibleUniverseCompanies.stream()
                                .map(c -> new InvestableUniverseCompanyBuilder()
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
                                        //Fund related changes
                                        .withEsgMsciScore(c.getEsgMsciScore())
                                        .withFdNr(c.getFdNr())
                                        .withFdA(c.getFdA())
                                        .withFdAA(c.getFdAA())
                                        .withFdAAA(c.getFdAAA())
                                        .withFdB(c.getFdB())
                                        .withFdBB(c.getFdBB())
                                        .withFdBBB(c.getFdBBB())
                                        .withFdCCC(c.getFdCCC())
                                        .withFundEsgScore(c.getFundEsgScore())
                                        .build()
                                ).collect(Collectors.toList());

                    log.info("Filtered Investable Universe Companies : " + alternativeCompanies);
                    if (alternativeCompanies != null && alternativeCompanies.size() > 0) {
                        log.info("Save companies in cache. Investable Universe Companies : " + alternativeCompanies);
                        for (AlternativesCompany alternativeCompany : alternativeCompanies) {
                            dataStore.put(alternativeCompany.getIsin(), alternativeCompany);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Exception Meassage : ", e.getMessage());
                return alternativeCompanies;
            }
        }

        //Mark the companies which belong to the portfolio as TRUE
        List<String> isinList = portfolio.getCompanies().stream().map(c -> c.getIsin()).collect(Collectors.toList());
        for (AlternativesCompany altComp : alternativeCompanies ) {
            if(isinList.contains(altComp.getIsin())){
                altComp.setIsPortfolioCompany(Boolean.TRUE);
            }else{
                altComp.setIsPortfolioCompany(Boolean.FALSE);
            }
        }

        log.info("Return value : " + alternativeCompanies);
        return alternativeCompanies;
    }
}
