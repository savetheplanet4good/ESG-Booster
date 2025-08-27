package org.synechron.portfolio.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.synechron.esg.model.Portfolio;
import org.synechron.esg.model.PortfolioHistoryResponse;
import org.synechron.portfolio.response.dto.ESGScore;


@Service
public class CalculationServiceProxyFallback implements CalculationServiceProxy {

    @Override
    public ResponseEntity<Portfolio> calculate(Portfolio portfolio) {
       return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<PortfolioHistoryResponse>> getHistoricalData(Portfolio portfolio) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
	@Override
	public ResponseEntity<Double> getPortfolioInsights(Portfolio portfolio) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<ESGScore> getSingleComponentInsights(Portfolio portfolio) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
