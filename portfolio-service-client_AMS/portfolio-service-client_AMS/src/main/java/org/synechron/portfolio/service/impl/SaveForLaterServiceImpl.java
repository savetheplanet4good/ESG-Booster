package org.synechron.portfolio.service.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.model.*;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.request.NewsSentimentRequest;
import org.synechron.portfolio.request.SaveForLaterRequest;
import org.synechron.esg.model.Alternatives;
import org.synechron.portfolio.response.dto.RestResponse;
import org.synechron.portfolio.response.dto.SaveForLaterResponse;
import org.synechron.portfolio.service.CalculationService;
import org.synechron.portfolio.service.SaveForLaterService;
import org.synechron.portfolio.utils.PortfolioUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class SaveForLaterServiceImpl implements SaveForLaterService {

    @Autowired
    HazelcastInstance hazelcastInstance;

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private NewsServiceProxy newsServiceProxy;

    private static final Logger log = LoggerFactory.getLogger(SaveForLaterServiceImpl.class);

    public SaveForLaterResponse saveForLaterPortfolio(SaveForLaterRequest saveForLaterRequest) {
        SaveForLaterResponse saveForLaterResponse = new SaveForLaterResponse();
        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");

        try {

            Portfolio portfolio = dataStore.get(saveForLaterRequest.getPortfolioId());
            List<Company> companies = portfolio.getCompanies();

            List<EnvironmentalFactor> environmentalFactors = new ArrayList<EnvironmentalFactor>();
            List<SocialFactor> socialFactors = new ArrayList<SocialFactor>();
            List<GovernanceFactor> governanceFactors = new ArrayList<GovernanceFactor>();

            //Remove influencer companies to be replaced
            for(String isin: saveForLaterRequest.getCompaniesToReplace()){
                companies.removeIf(company->company.getIsin().equals(isin));
            }

            //Add alternatives
            for (Alternatives alternative : saveForLaterRequest.getAlternative()) {
                companies.add(new Company(alternative.getIsin(), alternative.getName(),  alternative.getHoldingValue()));
            }

            //Set Portfolio object properties
            portfolio.setCompanies(companies);
            portfolio.setPortfolioId(PortfolioUtilities.randomPortfolioIdGenerator());
            if (!checkIfNameDuplicate(saveForLaterRequest.getPortfolioName())) {
                portfolio.setPortfolioName(saveForLaterRequest.getPortfolioName());
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Portfolio Name '" + saveForLaterRequest.getPortfolioName() + "' already exists. Please try with different Portfolio Name.");
            }

            //Calculate esg score
            RestResponse calculationresponse = calculationService.calculateESGforPortfolio(portfolio);
            if (calculationresponse.getStatus().equals(HttpStatus.OK)) {
                Portfolio calculatedPortfolio = (Portfolio) calculationresponse.getData();

                //Get news sentiment score for portfolio companies
                List<String> isinList = PortfolioUtils.getISINList(calculatedPortfolio);
                Map<String, Double> newsSentimentScoreMap = newsServiceProxy.getNewsSentimentScore(new NewsSentimentRequest(isinList, Constant.NEWS_SENTIMENT_FOR_NO_OF_MONTHS)).getBody();
                calculatedPortfolio = new PortfolioServiceImpl().mapNewsSentimentScoreToCompanies(calculatedPortfolio, newsSentimentScoreMap);

                dataStore.put(calculatedPortfolio.getPortfolioId(), calculatedPortfolio);

                saveForLaterResponse.setStatus(HttpStatus.OK);
                saveForLaterResponse.setMessage("Portfolio Save and Clone completed Successfully.");
            } else {
                saveForLaterResponse.setMessage(calculationresponse.getError().toString());
                saveForLaterResponse.setStatus(calculationresponse.getStatus());
            }

        } catch (ResponseStatusException responseStatusException) {
            saveForLaterResponse.setStatus(responseStatusException.getStatus());
            saveForLaterResponse.setMessage(responseStatusException.getReason());
            log.error(Arrays.toString(responseStatusException.getStackTrace()));
            log.error(responseStatusException.getMessage());
        }catch (Exception exception) {
            saveForLaterResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            saveForLaterResponse.setMessage("Exception occurred. Please Contact System Admin");
            log.error(Arrays.toString(exception.getStackTrace()));
            log.error(exception.getMessage());
        }
        return saveForLaterResponse;
    }

    private Boolean checkIfNameDuplicate(String portfolioName) {

        Boolean isPortfolioNameDuplicate = false;
        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
        dataStore.loadAll(Boolean.TRUE);
        for (Map.Entry<String, Portfolio> entry : dataStore.entrySet()) {
            if (portfolioName.equalsIgnoreCase(dataStore.get(entry.getKey()).getPortfolioName())) {
                isPortfolioNameDuplicate = true;
                break;
            }
        }
        return isPortfolioNameDuplicate;
    }
}
