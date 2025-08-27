package org.synechron.portfolio.service.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.synechron.portfolio.response.dto.BenchmarkDataLakeResponse;

import java.util.List;

@FeignClient(fallback = BenchmarkServiceProxyFallback.class, url = "${benchmark.url}", value = "benchmark")
public interface BenchmarkServiceProxy {

    @RequestMapping(value = "/benchmark/benchmark-data", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<List<BenchmarkDataLakeResponse>> getBenchmark();
}
