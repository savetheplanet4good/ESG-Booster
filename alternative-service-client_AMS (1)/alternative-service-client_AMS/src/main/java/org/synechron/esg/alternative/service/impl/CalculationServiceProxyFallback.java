package org.synechron.esg.alternative.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.synechron.esg.model.Portfolio;


/**
 * The type Calculation service proxy fallback.
 */
@Service
public class CalculationServiceProxyFallback implements CalculationProxy {

    @Override
    public ResponseEntity<Portfolio> calculate(Portfolio portfolio) {
        return null;
    }
}
