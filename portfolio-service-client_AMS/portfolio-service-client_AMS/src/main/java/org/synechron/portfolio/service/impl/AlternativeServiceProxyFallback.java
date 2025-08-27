package org.synechron.portfolio.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.synechron.portfolio.request.AlternativeRequest;
import org.synechron.esg.model.AlternativeResponse;


@Service
public class AlternativeServiceProxyFallback implements AlternativeServiceProxy {


    @Override
    public ResponseEntity<AlternativeResponse> getAlternative(AlternativeRequest alternativeRequest) {

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
