package org.synechron.esg.masteruniverse.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.synechron.esg.masteruniverse.dto.CompanyESGScore;

import java.util.List;
import java.util.Map;

@Service
public class DataLakeServiceProxyFallback implements DataLakeServiceProxy {

    @Override
    public ResponseEntity<List<CompanyESGScore>> getAllInvestibleCompanies() {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
