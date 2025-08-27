package org.synechron.portfolio.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.synechron.esg.model.CompanyCardAndESGDetailsDatalakeDto;
import org.synechron.esg.model.CompanyESGScore;
import org.synechron.portfolio.dto.DailyESGScoreRequest;
import org.synechron.portfolio.response.dto.*;
import org.synechron.portfolio.response.history.CarbonEmissionHistoricalGraph;

@Service
public class DataLakeServiceProxyFallback implements DataLakeServiceProxy {

    @Override
    public ResponseEntity<List<CompanyESGScore>> getCompaniesESGScore(List<String> isinCodes) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<CompanyESGScore>> getCompanies() {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<CompanyESGScore>> getAllInvestibleCompanies() {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<CompanyHistoricalData>> getHistoricalData(String isin, String investibleUniverseType) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<CompanyHistoricalDataForForecast>> getHistoricalDataForForecast(String isin, String investibleUniverseType) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<CompanyESGScore> getCompanyData(String isin) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

	@Override
	public ResponseEntity<List<CompanyESGScore>> getCompanyESGScoreByCompanyName(List<String> companyNames) {
		  return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

    @Override
    public ResponseEntity<CompanyCardAndESGDetailsDatalakeDto> getCompanyCardDetails(String isin) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
	@Override
	public ResponseEntity<CarbonEmissionHistoricalGraph> getCarbonEmissionHistData(String isin, String investibleUniverseType) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);	
    }

    @Override
    public ResponseEntity<List<CompanyPeerAverageScore> > getCompanyPeerAverageScore(String isin) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

	@Override
	public ResponseEntity<List<CompanyDailyESGAndSocialPositionigData>> getCompanyDailyESGScore(DailyESGScoreRequest dailyESGScoreRequest) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

    @Override
    public ResponseEntity<List<FundCompanyDTO>> getFundCompanies(String fundIsin) {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
