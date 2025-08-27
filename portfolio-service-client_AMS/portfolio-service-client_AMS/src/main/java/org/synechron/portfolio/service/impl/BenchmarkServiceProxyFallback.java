package org.synechron.portfolio.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.synechron.portfolio.response.dto.BenchmarkDataLakeResponse;

import java.util.List;

@Service
public class BenchmarkServiceProxyFallback implements BenchmarkServiceProxy {

    @Override
    public ResponseEntity<List<BenchmarkDataLakeResponse>> getBenchmark() {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
