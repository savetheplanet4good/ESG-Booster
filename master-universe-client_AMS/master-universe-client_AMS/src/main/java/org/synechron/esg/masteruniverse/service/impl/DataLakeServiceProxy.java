package org.synechron.esg.masteruniverse.service.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synechron.esg.masteruniverse.dto.CompanyESGScore;

import java.util.List;
import java.util.Map;

@FeignClient(fallback = DataLakeServiceProxyFallback.class, url = "${datalake.url}", value = "dl")
public interface DataLakeServiceProxy {

    @RequestMapping(value = "/datalake/all-investible-universe-companies", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<List<CompanyESGScore>> getAllInvestibleCompanies();
}
