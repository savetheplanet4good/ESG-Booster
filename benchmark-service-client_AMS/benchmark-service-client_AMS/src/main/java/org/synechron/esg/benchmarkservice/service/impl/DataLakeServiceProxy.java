package org.synechron.esg.benchmarkservice.service.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.synechron.esg.benchmarkservice.request.BenchmarkRequest;
import org.synechron.esg.benchmarkservice.response.dto.BenchmarkDataLakeResponse;

import java.util.List;

/**
 *
 * Interface Name : DataLakeServiceProxy
 * Purpose : Proxy created to communicate with the datalake using feign client.
 *
 */
@FeignClient(fallback = DataLakeServiceProxyFallback.class, url = "${datalake.url}", value = "dl")
public interface DataLakeServiceProxy {

    /**
     *
     * Function Name : getBenchmarkDataFromDatalake()
     * Purpose : Proxy datalake endpoint.
     * @param benchmarkRequest : BenchmarkRequest object in form of RequestBody
     * @return : BenchmarkDataLakeResponse object in form of response entity.
     *
     */
    @RequestMapping(value = "/datalake/benchmark-data", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<List<BenchmarkDataLakeResponse>> getBenchmarkDataFromDatalake(@RequestBody BenchmarkRequest benchmarkRequest);
}
