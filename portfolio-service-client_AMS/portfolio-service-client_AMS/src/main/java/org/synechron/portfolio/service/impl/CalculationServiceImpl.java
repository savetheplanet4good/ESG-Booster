package org.synechron.portfolio.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.synechron.esg.model.ApplicationComponentSettings;
import org.synechron.esg.model.Portfolio;
import org.synechron.esg.model.PortfolioHistoryResponse;
import org.synechron.portfolio.application_settings.service.ApplicationComponentSettingsService;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.response.dto.ESGScore;
import org.synechron.portfolio.response.dto.RestResponse;
import org.synechron.portfolio.response.history.HistoryResponse;
import org.synechron.portfolio.service.CalculationService;
import org.synechron.portfolio.transform.HistoryTransform;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Service
public class CalculationServiceImpl implements CalculationService {

    @Autowired
    private CalculationServiceProxy calculationServiceProxy;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private ApplicationComponentSettingsService applicationComponentSettingsService;

    public RestResponse calculateESGforPortfolio(Portfolio portfolio) throws Exception {

        RestResponse response = new RestResponse(HttpStatus.OK, "OK");
        Portfolio calculatedPortfolio = calculationServiceProxy.calculate(portfolio).getBody();
        response.setData(calculatedPortfolio);
        return response;
    }

    @Override
    public HistoryResponse getHistoricalData(String portfolioId, String responseType) throws IOException {

        String cacheKey = "";
        List<PortfolioHistoryResponse> response;
        IMap<String, List<PortfolioHistoryResponse>> portfolioHistoricalPerformanceResponseIMap = hazelcastInstance.getMap("portfolioCalculationHistory");
        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
        Portfolio portfolio = dataStore.get(portfolioId);

        if(applicationComponentSettingsMap.get(Constant.IS_HISTORICAL_PERFORMANCE_ENABLED).getValue().equals(Boolean.TRUE)) {

            if(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE.equalsIgnoreCase(portfolio.getInvestableUniverseType()))
                cacheKey = Constant.DS1;
            else if(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE.equalsIgnoreCase(portfolio.getInvestableUniverseType()))
                cacheKey = Constant.DS2;
            else if(Constant.MSCI_INVESTIBLE_UNVIERSE_TYPE.equalsIgnoreCase(portfolio.getInvestableUniverseType()))
                cacheKey = Constant.DS3;
            else{
                //throw error
            }

            //Fetch from cache
            response = portfolioHistoricalPerformanceResponseIMap.get(portfolioId + "_" + cacheKey);
            if (response == null || response.isEmpty()) {
                response = calculationServiceProxy.getHistoricalData(portfolio).getBody();
                portfolioHistoricalPerformanceResponseIMap.put(portfolioId + "_" + cacheKey, response);
            }
        }else{
            response = null;
        }

        return HistoryTransform.transform(response, responseType, portfolio.getPortfolioIsinsType());
    }
    
	@Override
	public Double getPortfolioInsights(Portfolio portfolio) {
		return calculationServiceProxy.getPortfolioInsights(portfolio).getBody();
	}

	@Override
	public ESGScore getSingleComponentInsights(Portfolio portfolio) {
		return calculationServiceProxy.getSingleComponentInsights(portfolio).getBody();
	}
}
