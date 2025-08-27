package org.synechron.portfolio.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.synechron.portfolio.response.dto.InvestSuitResponse;

@FeignClient(url = "https://dev.storyteller.investsuite.com/", name = "invest")
public interface InvestSuitProxy {

    @PostMapping(value = "api/esg", produces = {"application/json"})
    public ResponseEntity<InvestSuitResponse> getAltrenatives(@RequestBody String request);
}
