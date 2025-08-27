package org.synechron.esg.benchmarkservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.benchmarkservice.request.BenchmarkRequest;
import org.synechron.esg.benchmarkservice.response.dto.BenchmarkDataLakeResponse;
import org.synechron.esg.benchmarkservice.service.BenchmarkService;

import java.util.List;

/**
 *
 * Class Name : BenchmarkServiceImpl
 * Purpose : This class is a contains the business logic for the BenchmarkService service methods, basically the service implemention code.
 *
 */
@Service
public class BenchmarkServiceImpl implements BenchmarkService {

    /**
     * This property's value is configured inside properties file from where it will pick.
     */
    @Value("#{'${list.benchmark.datasource}'.split(',')}")
    private List<String> dataSourcesList;

    /**
     * This property's value is configured inside properties file from where it will pick.
     */
    @Value("#{'${list.benchmark.index}'.split(',')}")
    private List<String> indexList;

    /**
     * Class logger.
     */
    private static final Logger log = LoggerFactory.getLogger(BenchmarkServiceImpl.class);

    /**
     * Autowiring of DataLakeServiceProxy to communicate with datalake services.
     */
    @Autowired
    private DataLakeServiceProxy dataLakeServiceProxy;

    /**
     *
     * Function Name : getBenchmarkData()
     * Purpose : Retrieve the benchmark data by calling the datalake service.
     *
     */
    @Override
    public List<BenchmarkDataLakeResponse> getBenchmarkData() {

        BenchmarkRequest benchmarkRequest = new BenchmarkRequest(dataSourcesList, indexList);

        log.info("Calling datalake service for data.");
        ResponseEntity<List<BenchmarkDataLakeResponse>> benchmarkDataFromDatalakeResponseEntity = dataLakeServiceProxy.getBenchmarkDataFromDatalake(benchmarkRequest);

        log.info("Validating the request and data received from datalake is correct.");
        if (benchmarkDataFromDatalakeResponseEntity.getStatusCode().equals(HttpStatus.OK) && !benchmarkDataFromDatalakeResponseEntity.getBody().isEmpty()) {
            if (benchmarkDataFromDatalakeResponseEntity.getBody().size() != 12) {
                log.error("Benchmark data fetched is incorrect or incomplete.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Benchmark data fetched is incorrect or incomplete.");
            }
        } else {
            log.error("Error occurred while retreiving benchmark data from data lake or Empty data.");
            throw new ResponseStatusException(benchmarkDataFromDatalakeResponseEntity.getStatusCode(), "Error occurred while retreiving benchmark data from data lake or Empty data.");
        }

        log.info("Benchmark data from datalakes response : " + benchmarkDataFromDatalakeResponseEntity.getBody());
        return benchmarkDataFromDatalakeResponseEntity.getBody();
    }
}
