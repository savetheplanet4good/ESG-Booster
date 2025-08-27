package org.synechron.esg.benchmarkservice.service;

import org.springframework.http.ResponseEntity;
import org.synechron.esg.benchmarkservice.response.dto.BenchmarkDataLakeResponse;

import java.util.List;

/**
 *
 * Interface Name : BenchmarkService
 * Purpose : This interface will contain all the services that can be accessed.
 *
 */
public interface BenchmarkService {

    /**
     *
     * Function Name : getBenchmarkData()
     * Purpose : Retrieve the benchmark data.
     *
     */
    public List<BenchmarkDataLakeResponse> getBenchmarkData();
}
