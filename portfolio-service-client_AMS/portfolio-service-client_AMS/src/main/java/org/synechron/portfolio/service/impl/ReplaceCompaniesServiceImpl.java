package org.synechron.portfolio.service.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.synechron.esg.model.*;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.request.NewsSentimentRequest;
import org.synechron.portfolio.request.ReplaceCompaniesRequest;
import org.synechron.esg.model.Alternatives;
import org.synechron.portfolio.response.dto.ReplaceCompanyResponse;
import org.synechron.portfolio.response.dto.RestResponse;
import org.synechron.portfolio.service.CalculationService;
import org.synechron.portfolio.service.ReplaceCompaniesService;
import org.synechron.portfolio.utils.PortfolioUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ReplaceCompaniesServiceImpl implements ReplaceCompaniesService {
    private static final Logger log = LoggerFactory.getLogger(ReplaceCompaniesServiceImpl.class);

    @Autowired
    HazelcastInstance hazelcastInstance;

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private NewsServiceProxy newsServiceProxy;

    public ReplaceCompanyResponse replaceCompanies(ReplaceCompaniesRequest request) {
        ReplaceCompanyResponse response = new ReplaceCompanyResponse(200, "Success", "Company Replaced Sucessfully");
        IMap<String, Portfolio> dataStore =
                hazelcastInstance.getMap("portfolios");

        try {

            Portfolio portfolio = dataStore.get(request.getPortfolioId());
            List<Company> companies = portfolio.getCompanies();

            List<EnvironmentalFactor> environmentalFactors = new ArrayList<EnvironmentalFactor>();
            List<SocialFactor> socialFactors = new ArrayList<SocialFactor>();
            List<GovernanceFactor> governanceFactors = new ArrayList<GovernanceFactor>();

            for (String isin : request.getCompaniesToReplace()) {
                companies.removeIf(company -> company.getIsin().equals(isin));
            }

            for (Alternatives alternative : request.getAlternative()) {
                companies.add(new Company(alternative.getIsin(), alternative.getName(), alternative.getHoldingValue()));
            }

            portfolio.setCompanies(companies);
            RestResponse calculationresponse = calculationService.calculateESGforPortfolio(portfolio);
            if (calculationresponse.getStatus().equals(HttpStatus.OK)) {
                Portfolio calculatedPortfolio = (Portfolio) calculationresponse.getData();

                //Get news sentiment score for portfolio companies
                List<String> isinList = PortfolioUtils.getISINList(calculatedPortfolio);
                Map<String, Double> newsSentimentScoreMap = newsServiceProxy.getNewsSentimentScore(new NewsSentimentRequest(isinList, Constant.NEWS_SENTIMENT_FOR_NO_OF_MONTHS)).getBody();
                calculatedPortfolio = new PortfolioServiceImpl().mapNewsSentimentScoreToCompanies(calculatedPortfolio, newsSentimentScoreMap);

                dataStore.put(calculatedPortfolio.getPortfolioId(), calculatedPortfolio);

                //Clear master universe cache after replacing the companies with alternatives
                IMap<String, AlternativesCompany> dataStoreInvestableUniverse = hazelcastInstance.getMap("alternativeComapnies");
                dataStoreInvestableUniverse.evictAll();

                //Clear portfolio historical entry for this portfolio from cache after replacing the companies with alternatives
                IMap<String, List<PortfolioHistoryResponse>> portfolioHistoricalPerformanceResponseIMap = hazelcastInstance.getMap("portfolioCalculationHistory");
                List<PortfolioHistoryResponse> portfolioHistoryResponses = portfolioHistoricalPerformanceResponseIMap.get(portfolio.getPortfolioId() + "_" + (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE) ? "DS1" : "DS2"));
                if(portfolioHistoryResponses != null)
                    portfolioHistoricalPerformanceResponseIMap.remove(portfolio.getPortfolioId() + "_" + (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE) ? "DS1" : "DS2"));

            } else {
                response.setMessage(calculationresponse.getError().toString());
                response.setStatusCode(calculationresponse.getStatus().value());
                response.setStatus(calculationresponse.getMessage());
            }

        } catch (Exception exception) {
            response.setStatus("Error");
            response.setMessage("Please contact System Admin");
            response.setStatusCode(300);
            log.error(Arrays.toString(exception.getStackTrace()));
            log.error(exception.getMessage());

        }
        return response;

    }
}
