package org.synechron.esg.benchmarkservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.esg.benchmarkservice.response.dto.BenchmarkDataLakeResponse;
import org.synechron.esg.benchmarkservice.service.BenchmarkService;

import java.util.List;

/**
 *
 * Class Name : BenchmarkController
 * Purpose : This class is a benchmark controller where we have all the endpoints related to benchmark.
 *
 */
@RestController
public class BenchmarkController {

    /**
     * Autowiring of Benchmark Service to access the services
     */
    @Autowired
    private BenchmarkService benchmarkService;

    /**
     *
     * Function Name : getBenchmarkData()
     * Purpose : Retrieve the benchmark data.
     *
     */
    @GetMapping("/benchmark-data")
    public ResponseEntity<?> getBenchmarkData() {
        try {
            List<BenchmarkDataLakeResponse> benchmarkData = benchmarkService.getBenchmarkData();
            return new ResponseEntity<>(benchmarkData, getNoCacheHeaders(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error Occured while retrieving benchmark data from Datalake", getNoCacheHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    private HttpHeaders getNoCacheHeaders() {
        HttpHeaders responseHeaders=new HttpHeaders();
        responseHeaders.set("Cache-Control","no-cache");
        return responseHeaders;
    }
}
