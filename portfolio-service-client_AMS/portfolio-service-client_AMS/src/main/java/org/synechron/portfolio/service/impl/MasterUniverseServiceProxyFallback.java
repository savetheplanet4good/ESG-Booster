package org.synechron.portfolio.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.request.UpdateInvestmentUniverseFilterRequest;
import org.synechron.portfolio.response.dto.InvestibleUniverseResponse;
import org.synechron.portfolio.response.dto.UpdateInvestibleUniverseResponse;

@Service
public class MasterUniverseServiceProxyFallback implements MasterUniverseServiceProxy {

    @Override
    public ResponseEntity<InvestibleUniverseResponse> getInvestibleUniverse(@RequestBody Portfolio portfolio){
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<UpdateInvestibleUniverseResponse> updateInvetibleUniverseFilter(@PathVariable String portfolioId, @RequestBody UpdateInvestmentUniverseFilterRequest request){
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
