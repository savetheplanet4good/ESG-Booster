package org.synechron.portfolio.service.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.request.UpdateInvestmentUniverseFilterRequest;
import org.synechron.portfolio.response.dto.InvestibleUniverseResponse;
import org.synechron.portfolio.response.dto.UpdateInvestibleUniverseResponse;

@FeignClient(fallback = MasterUniverseServiceProxyFallback.class, url = "${master-universe.url}", value = "mu")
public interface MasterUniverseServiceProxy {

    @RequestMapping(value = "/master-universe/fetch-investible-universe/{portfolioId}", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<InvestibleUniverseResponse> getInvestibleUniverse(@RequestBody Portfolio portfolio);

    @RequestMapping(value = "/master-universe/update-investible-universe/{portfolioId}", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<UpdateInvestibleUniverseResponse> updateInvetibleUniverseFilter(@PathVariable String portfolioId, @RequestBody UpdateInvestmentUniverseFilterRequest request);
}
