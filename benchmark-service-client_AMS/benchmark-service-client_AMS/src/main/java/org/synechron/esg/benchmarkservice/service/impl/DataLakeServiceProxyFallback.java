package org.synechron.esg.benchmarkservice.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.synechron.esg.benchmarkservice.request.BenchmarkRequest;
import org.synechron.esg.benchmarkservice.response.dto.BenchmarkDataLakeResponse;

import java.util.List;

/**
 *
 * Class Name : DataLakeServiceProxyFallback
 * Purpose : This class handle fallback in case of failure request.
 *
 */
@Service
public class DataLakeServiceProxyFallback implements DataLakeServiceProxy {

    /**
     *
     * Function Name : getBenchmarkDataFromDatalake()
     * Purpose : Return error in case of failure.
     * @param benchmarkRequest : BenchmarkRequest object in form of RequestBody
     * @return : BenchmarkDataLakeResponse object in form of response entity.
     *
     */
    @Override
    public ResponseEntity<List<BenchmarkDataLakeResponse>> getBenchmarkDataFromDatalake(@RequestBody BenchmarkRequest benchmarkRequest) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
