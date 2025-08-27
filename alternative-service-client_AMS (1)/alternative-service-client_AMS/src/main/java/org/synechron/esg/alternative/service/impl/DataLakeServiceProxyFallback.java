package org.synechron.esg.alternative.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.synechron.esg.model.CompanyESGScore;

import java.util.List;

/**
 * The type Data lake service proxy fallback.
 */
@Service
public class DataLakeServiceProxyFallback implements DataLakeServiceProxy {

    @Override
    public ResponseEntity<List<CompanyESGScore>> getCompaniesESGScore(List<String> isinCodes) {
        // TODO Auto-generated method stub
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<List<CompanyESGScore>> getAllInvestibleCompanies() {
        // TODO Auto-generated method stub

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
