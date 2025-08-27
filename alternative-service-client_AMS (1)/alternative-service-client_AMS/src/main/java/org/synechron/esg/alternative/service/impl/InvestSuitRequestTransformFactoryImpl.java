package org.synechron.esg.alternative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.synechron.esg.alternative.service.AlternativeRequestTransformService;
import org.synechron.esg.alternative.service.InvestSuitRequestTransformFactory;

/**
 * The type Invest suit request transform factory.
 */
@Service
public class InvestSuitRequestTransformFactoryImpl implements InvestSuitRequestTransformFactory {

    @Autowired
    @Qualifier("refinitiveRequestTransformServiceImpl")
    private AlternativeRequestTransformService refinitive;


    @Autowired
    @Qualifier("yahooFinanceRequestTransformServiceImpl")
    private AlternativeRequestTransformService sustainAnalytics;


    @Override
    public AlternativeRequestTransformService getAlternativeTransformServiceImpl(String investibleUniverseType) {
        AlternativeRequestTransformService response = refinitive;

        switch (investibleUniverseType) {

            case "Sustainanalytics":
                response = sustainAnalytics;
                break;
            case "SUSTAINALYTICS":
                response = sustainAnalytics;
                break;
            default:
                response = refinitive;
        }
        return response;
    }
}
