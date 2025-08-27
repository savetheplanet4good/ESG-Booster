package org.synechron.esg.alternative.service.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synechron.esg.model.CompanyESGScore;

import java.util.List;

/**
 * The interface Data lake service proxy.
 */
@FeignClient(fallback = DataLakeServiceProxyFallback.class, url = "${datalake.url}", value = "dl")
public interface DataLakeServiceProxy {

    /**
     * Gets companies esg score.
     *
     * @param isinCodes the isin codes
     * @return the companies esg score
     */
    @RequestMapping(value = "/datalake/companyesg", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<List<CompanyESGScore>> getCompaniesESGScore(@RequestBody List<String> isinCodes);

    /**
     * Gets all investible companies.
     *
     * @return the all investible companies
     */
    @RequestMapping(value = "/datalake/all-investible-universe-companies", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<List<CompanyESGScore>> getAllInvestibleCompanies();


}
