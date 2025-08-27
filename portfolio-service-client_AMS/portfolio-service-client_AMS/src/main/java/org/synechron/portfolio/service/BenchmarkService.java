package org.synechron.portfolio.service;

import org.synechron.portfolio.response.dto.BenchMarkResponseDto;
import org.synechron.portfolio.response.dto.FundsBenchmarkResponseDto;

import java.io.IOException;

public interface BenchmarkService {

    public BenchMarkResponseDto getBenchMarkDataForPortfolio(String portfolioId) throws IOException;

    public FundsBenchmarkResponseDto getFundBenchmarksForPortfolio(String portfolioId) throws IOException;
}
