package org.synechron.esg.alternative.service.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.synechron.esg.model.Portfolio;

/**
 * The interface Calculation proxy.
 */
@FeignClient(fallback = CalculationServiceProxyFallback.class, url = "${calculation.url}", value = "calc")
public interface CalculationProxy {

    /**
     * Calculate response entity.
     *
     * @param portfolio the portfolio
     * @return the response entity
     */
    @RequestMapping(value = "/calculation/calculate", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<Portfolio> calculate(@RequestBody Portfolio portfolio);


}
