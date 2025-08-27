package org.synechron.esg.alternative.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.synechron.esg.alternative.error.InvalidResponseException;
import org.synechron.esg.alternative.request.AlternativeRequest;
import org.synechron.esg.alternative.response.AlternativeResponse;
import org.synechron.esg.alternative.service.Alternative;
import org.synechron.esg.alternative.service.AlternativeFactory;
import org.synechron.esg.alternative.service.AlternativeService;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Alternative service.
 */
@Service
public class AlternativeServiceImpl implements AlternativeService {

    @Autowired
    private AlternativeFactory alternativeFactory;


    @Value("${alternative.type}")
    private String alternativeType;

    private static final Logger log = LoggerFactory.getLogger(AlternativeServiceImpl.class);


    @Override
    public AlternativeResponse getAlternatives(AlternativeRequest alternativeRequest) {

            AlternativeResponse response = null;
            Alternative alternative = alternativeFactory.getAlternativeService(alternativeType);

            try{
                response = alternative.getAlternatives(alternativeRequest.getIsin(),
                        alternativeRequest.getPortfolioId(), alternativeRequest.getEsgTargetType());
            }catch (Exception | InvalidResponseException exception){
                log.error("Exception in Getting alternative : {}",exception.getStackTrace());
                response = new AlternativeResponse(null, null, null, HttpStatus.INTERNAL_SERVER_ERROR, 500);
            }
            return response;

    }
}
