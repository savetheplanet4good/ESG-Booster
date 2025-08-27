package org.synechron.portfolio.service.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.synechron.portfolio.request.AlternativeRequest;
import org.synechron.esg.model.AlternativeResponse;

@FeignClient(fallback = AlternativeServiceProxyFallback.class, url = "${alternative.url}", value = "alternative")
public interface AlternativeServiceProxy {

    @RequestMapping(value = "/alternative-service/alternatives", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<AlternativeResponse> getAlternative(@RequestBody AlternativeRequest alternativeRequest);

}
