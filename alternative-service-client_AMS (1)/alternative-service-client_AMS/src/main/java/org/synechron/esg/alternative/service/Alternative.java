package org.synechron.esg.alternative.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.http.HttpStatus;
import org.synechron.esg.alternative.constant.Constant;
import org.synechron.esg.alternative.dto.AlternativesObject;
import org.synechron.esg.alternative.dto.cachekey.AlternativeCacheKey;
import org.synechron.esg.alternative.enums.PortfolioTypeEnum;
import org.synechron.esg.alternative.error.InvalidResponseException;
import org.synechron.esg.alternative.response.*;
import org.synechron.esg.alternative.response.buiilder.AlternativesCompanyBuilder;
import org.synechron.esg.alternative.utils.AlternativeUtils;
import org.synechron.esg.model.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The interface Alternative.
 */
public interface Alternative {

    /**
     * Convert to alternatives list.
     *
     * @param response               the response
     * @param investibleUniverseType the investible universe type
     * @param dataLakeResponse       the data lake response
     * @param weitage                the weitage
     * @return the list
     */
    default List<Alternatives> convertToAlternatives(InvestSuitResponse response, String investibleUniverseType ,
                                                     List<CompanyESGScore> dataLakeResponse , Double weitage) {

        List<Alternatives> resp = new ArrayList<>();
        Map<String, Double> recomendedMap = response.getRecommended_trades();
        List<String> responseISIN = new ArrayList<>();
        int alternativeId = 1;
        // taking only positive values
        for (Map.Entry<String, Double> entry : recomendedMap.entrySet()) {
            if (entry.getValue() > 0.00)
                responseISIN.add(entry.getKey());
        }

        if (recomendedMap == null || recomendedMap.isEmpty()) {
            return resp;
        }

        //ResponseEntity<List<CompanyESGScore>> dataLakeResponse = dataLakeProxy.getCompaniesESGScore(responseISIN);
        for (CompanyESGScore companyESGScore : dataLakeResponse) {
            if (investibleUniverseType.equalsIgnoreCase(Constant.REFINITIV)) {
                resp.add(new Alternatives(alternativeId, companyESGScore.getIsin(), companyESGScore.getInstrumentName(),
                        companyESGScore.getEsgCombinedScore(),
                        AlternativeUtils.formatDouble(recomendedMap.get(companyESGScore.getIsin()) * weitage),
                        1.00, AlternativeUtils.formatDouble(companyESGScore.getEsgCombinedScore()),
                        AlternativeUtils.formatDouble(companyESGScore.getEnvironmentPillarScore()),
                        AlternativeUtils.formatDouble(companyESGScore.getSocialPillarScore()),
                        AlternativeUtils.formatDouble(companyESGScore.getGovernancePillarScore())));
            } else if (investibleUniverseType.equalsIgnoreCase(Constant.SUSTAINALYTICS)) {
                resp.add(new Alternatives(alternativeId, companyESGScore.getIsin(), companyESGScore.getInstrumentName(),
                        AlternativeUtils.formatDouble(companyESGScore.getSustainalyticsTotalEsgScore()),
                        AlternativeUtils.formatDouble(recomendedMap.get(companyESGScore.getIsin()) * 100),
                        1.00, AlternativeUtils.formatDouble(companyESGScore.getSustainalyticsTotalEsgScore()),
                        AlternativeUtils.formatDouble(companyESGScore.getSustainalyticsEnvironmentScore()),
                        AlternativeUtils.formatDouble(companyESGScore.getSustainalyticsSocialScore()),
                        AlternativeUtils.formatDouble(companyESGScore.getSustainalyticsGovernanceScore())));
            } else {
                //throw error
            }
            alternativeId++;
        }
        //log.debug("Populated Alternatives {}", resp);
        return resp;
    }


    /**
     * Convert to historical performance response.
     * After receiving response from invest suit , Converting to Object needed for UI to display
     * @param historicalPerformance the historical performance
     * @return the historical performance response
     */
    default HistoricalPerformanceResponse convertToHistoricalPerformance(HistoricalPerformance historicalPerformance) {
        Map<String, Double> origionalPortfolio = historicalPerformance.getOriginal_portfolio();
        Map<String, Double> recomendedPortfolio = historicalPerformance.getRecommended_portfolio();

        List<HistoricalData> origionalPortfolioVal = new ArrayList<>();
        List<HistoricalData> recomendedPortfolioVal = new ArrayList<>();
        for (Map.Entry entry : origionalPortfolio.entrySet()) {
            origionalPortfolioVal.add(new HistoricalData(entry.getKey().toString().split(" ")[0],
                    AlternativeUtils.formatDouble((Double) entry.getValue())));
        }

        for (Map.Entry entry : recomendedPortfolio.entrySet()) {
            recomendedPortfolioVal.add(new HistoricalData(entry.getKey().toString().split(" ")[0],
                    AlternativeUtils.formatDouble((Double) entry.getValue())));
            recomendedPortfolioVal.add(new HistoricalData(entry.getKey().toString().split(" ")[0],
                    AlternativeUtils.formatDouble((Double) entry.getValue())));
        }

        HistoricalPerformanceResponse resp = new HistoricalPerformanceResponse(origionalPortfolioVal, recomendedPortfolioVal);

        return resp;
    }


    /**
     * Convert to alternatives list.
     *
     * @param alternatives the alternatives
     * @return the list
     */
    default List<AlternativesCompany> convertToAlternatives(List<CompanyESGScore> alternatives){

        List<AlternativesCompany> alternativeCompanies = null;
        if(alternatives != null){

            alternativeCompanies = alternatives.stream().filter(a -> !a.getIsinType().equalsIgnoreCase(PortfolioTypeEnum.Fund.getValue()))
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

        }
        return alternativeCompanies;

    }

    /**
     * Persist alternative to cache.
     *
     * @param isinCombination   the isin combination
     * @param portfolioId       the portfolio id
     * @param resp              the resp
     * @param esgTargetType     the esg target type
     * @param hazelcastInstance the hazelcast instance
     * @param dataLakeresponse  the data lakeresponse
     */
    default void persistAlternativeToCache(List<String> isinCombination, String portfolioId, AlternativeResponse resp, String esgTargetType
            , HazelcastInstance hazelcastInstance , List<CompanyESGScore> dataLakeresponse) {
        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
        Portfolio portfolio = dataStore.get(portfolioId);
        List<AlternativesCompany> inv = this.getInvestibleUniverse(portfolioId, hazelcastInstance,dataLakeresponse);
        inv = inv.stream().filter(a -> !a.getIsinType().equalsIgnoreCase(PortfolioTypeEnum.Fund.getValue())).collect(Collectors.toList());
        List<String> investibleUniverse = inv.stream().map(AlternativesCompany::getIsin).collect(Collectors.toList());
        Map<String, Double> currentPortfolio = portfolio.getCompanies().stream().collect(
                Collectors.toMap(Company::getName, Company::getHoldingValue));
        IMap<Integer, AlternativesObject> alternativesDataStore = hazelcastInstance.getMap("alternatives");
        AlternativeCacheKey alternativeCacheKey = new AlternativeCacheKey(isinCombination, portfolio.getInvestableUniverseFilter(), portfolio.getInvestableUniverseType(), currentPortfolio, esgTargetType);
        alternativesDataStore.put(alternativeCacheKey.hashCode(), new AlternativesObject(resp, alternativeCacheKey, alternativeCacheKey.hashCode(), new Date()));
    }


    /**
     * Gets investible universe.
     *
     * @param portfolioId       the portfolio id
     * @param hazelcastInstance the hazelcast instance
     * @param dataLakeResponse  the data lake response
     * @return the investible universe
     */
    default List<AlternativesCompany> getInvestibleUniverse(String portfolioId , HazelcastInstance hazelcastInstance , List<CompanyESGScore> dataLakeResponse) {
        // TODO Auto-generated method stub
        RestResponse response = new RestResponse(HttpStatus.OK, "OK");
        //ResponseEntity<List<CompanyESGScore>> dataLakeresponse = null;
        List<AlternativesCompany> alternativeCompanies = null;
        IMap<String, AlternativesCompany> dataStore = hazelcastInstance.getMap("alternativeComapnies");
        IMap<String, Portfolio> portfolioData = hazelcastInstance.getMap("portfolios");
        Portfolio portfolio = portfolioData.get(portfolioId);
        InvestableUniverseFilter filter = portfolio.getInvestableUniverseFilter();
        if (dataStore != null && dataStore.size() > 0) {
            alternativeCompanies = (List<AlternativesCompany>) dataStore.values();
        } else {
            try {
                //dataLakeresponse = dataLakeProxy.getAllInvestibleCompanies();
                if ( dataLakeResponse != null && !dataLakeResponse.isEmpty()) {
                    //List<CompanyESGScore> allInvestibleUniverseCompanies = (List<CompanyESGScore>) dataLakeresponse.getBody();
                    alternativeCompanies = this.convertToAlternatives(dataLakeResponse);
                    if (alternativeCompanies != null && alternativeCompanies.size() > 0) {
                        for (AlternativesCompany alternativeCompany : alternativeCompanies) {
                            dataStore.put(alternativeCompany.getIsin(), alternativeCompany);
                        }
                    }
                }
            } catch (Exception e) {
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setError("Error Occured while accessing the Data Lake Api");
                response.setMessage("Fetching Alternatives Companies  Failed");
                response.setData(null);
                return alternativeCompanies;
            }
        }

        List<String> isinList = AlternativeUtils.getISINList(portfolio);
        for (AlternativesCompany altComp : alternativeCompanies) {
            if (isinList.contains(altComp.getIsin())) {
                altComp.setIsPortfolioCompany(Boolean.TRUE);
            } else {
                altComp.setIsPortfolioCompany(Boolean.FALSE);
            }
        }

        return alternativeCompanies;
    }


    /**
     * Gets alternatives.
     *
     * @param isin          the isin
     * @param portfolioId   the portfolio id
     * @param esgTargetType the esg target type
     * @return the alternatives
     * @throws Exception                the exception
     * @throws InvalidResponseException the invalid response exception
     */
    public AlternativeResponse getAlternatives(List<String> isin, String portfolioId,
                                               String esgTargetType) throws Exception, InvalidResponseException;
}
