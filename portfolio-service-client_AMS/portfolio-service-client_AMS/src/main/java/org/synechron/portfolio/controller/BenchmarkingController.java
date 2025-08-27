package org.synechron.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.portfolio.response.dto.BenchMarkResponseDto;
import org.synechron.portfolio.response.dto.FundsBenchmarkResponseDto;
import org.synechron.portfolio.service.BenchmarkService;

import java.io.IOException;

@RestController
public class BenchmarkingController extends BaseController {

    @Autowired
    private BenchmarkService benchMarkService;

    @GetMapping(value = "/benchmark-data/{portfolioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BenchMarkResponseDto getBenchmarkData(@PathVariable String portfolioId) throws IOException {
        BenchMarkResponseDto benchmarkData = benchMarkService.getBenchMarkDataForPortfolio(portfolioId);
        return benchmarkData;
    }

    @GetMapping(value = "/funds-benchmark-data/{portfolioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FundsBenchmarkResponseDto getFundsBenchmarkData(@PathVariable String portfolioId) throws IOException {
        FundsBenchmarkResponseDto fundsBenchmarkResponseDto = benchMarkService.getFundBenchmarksForPortfolio(portfolioId);
        return fundsBenchmarkResponseDto;
    }
}
