package org.synechron.esg.alternative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.synechron.esg.alternative.enums.AlternativeType;
import org.synechron.esg.alternative.service.Alternative;
import org.synechron.esg.alternative.service.AlternativeFactory;

/**
 * The type Alternative factory.
 */
@Service
public class AlternativeFactoryImpl implements AlternativeFactory {

    @Autowired
    @Qualifier("investSuitService")
    private Alternative investSuitService;

    @Override
    public Alternative getAlternativeService(String alternativeServiceKey) {
        Alternative response = null;
        switch(AlternativeType.valueOf(alternativeServiceKey)) {
            case INVESTSUIT:
                response = investSuitService;
                break;
            default:
                response= investSuitService;
        }

        return response;
    }
}
