package org.synechron.portfolio.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.model.ApplicationComponentSettings;
import org.synechron.esg.model.Benchmark;
import org.synechron.portfolio.application_settings.service.ApplicationComponentSettingsService;
import org.synechron.portfolio.response.dto.*;
import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.service.BenchmarkService;


import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.synechron.portfolio.utils.PortfolioUtils;

@Service
public class BenchmarkServiceImpl implements BenchmarkService {

    @Autowired
    HazelcastInstance hazelcastInstance;

    @Autowired
    private BenchmarkServiceProxy benchmarkServiceProxy;

    @Autowired
    private ApplicationComponentSettingsService applicationComponentSettingsService;

    private static final Logger log = LoggerFactory.getLogger(BenchmarkServiceImpl.class);

    public BenchMarkResponseDto getBenchMarkDataForPortfolio(String portfolioId) throws IOException {

        BenchMarkResponseDto benchMarkResponseDto = new BenchMarkResponseDto();
        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        if(applicationComponentSettingsMap.get(Constant.IS_BENCHMARK_ENABLED).getValue().equals(Boolean.TRUE)) {

            IMap<String, List<Benchmark>> dataStore = hazelcastInstance.getMap("benchmarks");
            IMap<String, Portfolio> dataStorePortfolio = hazelcastInstance.getMap("portfolios");
            Portfolio portfolio = dataStorePortfolio.get(portfolioId);

            List<CumulativeBenchmark> cumulativeBenchmarks = new ArrayList<>();
            List<Benchmark> refinitivBenchmarks = new ArrayList<>();
            List<Benchmark> sustainalyticsBenchmarks = new ArrayList<>();

            //Getting data from data lake
            ResponseEntity<List<BenchmarkDataLakeResponse>> benmarkListResponseEntity = benchmarkServiceProxy.getBenchmark();
            if (benmarkListResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                if (benmarkListResponseEntity.getBody() == null) {
                    log.error("Error occurred while retrieving benchmark data from benchmark service or received null data.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while retrieving benchmark data from benchmark service or received null data.");
                }
            }

            //Storing data to cache
            dataStore.evictAll();

            List<BenchmarkDataLakeResponse> benchmarkDataLakeResponses = benmarkListResponseEntity.getBody().stream()
                    .filter(benchmarkDataLakeResponse -> !Constant.DS3.equalsIgnoreCase(benchmarkDataLakeResponse.getSource())).collect(Collectors.toList());

            for (BenchmarkDataLakeResponse benchmarkDataLakeResponse : benchmarkDataLakeResponses) {
                if (Constant.DS1.equalsIgnoreCase(benchmarkDataLakeResponse.getSource())) {
                    refinitivBenchmarks.add(new Benchmark(benchmarkDataLakeResponse.getIndexName(), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getIndexTotalScore()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getEnvIndexScore()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getSocialIndexScore()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getGovIndexScore()),
                            benchmarkDataLakeResponse.getEsgMsciScore(), benchmarkDataLakeResponse.getFdNr(), benchmarkDataLakeResponse.getFdA(), benchmarkDataLakeResponse.getFdAA(), benchmarkDataLakeResponse.getFdAAA(), benchmarkDataLakeResponse.getFdB(), benchmarkDataLakeResponse.getFdBB(), benchmarkDataLakeResponse.getFdBBB(), benchmarkDataLakeResponse.getFdCCC()));
                } else if (Constant.DS2.equalsIgnoreCase(benchmarkDataLakeResponse.getSource())) {
                    sustainalyticsBenchmarks.add(new Benchmark(benchmarkDataLakeResponse.getIndexName(), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getIndexTotalScore()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getEnvIndexScore()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getSocialIndexScore()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getGovIndexScore()),
                            benchmarkDataLakeResponse.getEsgMsciScore(), benchmarkDataLakeResponse.getFdNr(), benchmarkDataLakeResponse.getFdA(), benchmarkDataLakeResponse.getFdAA(), benchmarkDataLakeResponse.getFdAAA(), benchmarkDataLakeResponse.getFdB(), benchmarkDataLakeResponse.getFdBB(), benchmarkDataLakeResponse.getFdBBB(), benchmarkDataLakeResponse.getFdCCC()));
                } else {
                    log.error("Invalid data source.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
                }
            }
            dataStore.put(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE, refinitivBenchmarks);
            dataStore.put(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE, sustainalyticsBenchmarks);

            //Set value in response
            benchMarkResponseDto.setPortfolioId(portfolioId);
            benchMarkResponseDto.setPortfolioName(portfolio.getPortfolioName());

            if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)) {
                benchMarkResponseDto.setTotalESG(PortfolioUtilities.normaliseDecimals(portfolio.getEsgCombinedScore()));
                benchMarkResponseDto.setEnvironmentalScore(PortfolioUtilities.normaliseDecimals(portfolio.getEnvScore()));
                benchMarkResponseDto.setSocialScore(PortfolioUtilities.normaliseDecimals(portfolio.getSocialScore()));
                benchMarkResponseDto.setGovernanceScore(PortfolioUtilities.normaliseDecimals(portfolio.getGovScore()));
                benchMarkResponseDto.setBenchmarkdata(dataStore.get(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE));
                for (Benchmark benchmarkDataLakeResponse : dataStore.get(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)) {
                    Double cumulativeESGScore = ((portfolio.getEsgCombinedScore()
                            - benchmarkDataLakeResponse.getIndexTotalScore()) /
                            benchmarkDataLakeResponse.getIndexTotalScore()) * 100;

                    Double cumulativeEnvScore = ((portfolio.getEnvScore()
                            - benchmarkDataLakeResponse.getEnvIndexScore()) /
                            benchmarkDataLakeResponse.getEnvIndexScore()) * 100;

                    Double cumulativeSocialScore = ((portfolio.getSocialScore()
                            - benchmarkDataLakeResponse.getSocialIndexScore()) /
                            benchmarkDataLakeResponse.getSocialIndexScore()) * 100;

                    Double cumulativeGovScore = ((portfolio.getGovScore()
                            - benchmarkDataLakeResponse.getGovIndexScore()) /
                            benchmarkDataLakeResponse.getGovIndexScore()) * 100;

                    cumulativeBenchmarks.add(new CumulativeBenchmark(benchmarkDataLakeResponse.getIndexName(),
                            PortfolioUtils.formatDouble(cumulativeESGScore), PortfolioUtils.formatDouble(cumulativeEnvScore),
                            PortfolioUtils.formatDouble(cumulativeSocialScore), PortfolioUtils.formatDouble(cumulativeGovScore)));
                }

            } else if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE)) {
                benchMarkResponseDto.setTotalESG(PortfolioUtilities.normaliseDecimals(portfolio.getSustainalyticsCombinedScore()));
                benchMarkResponseDto.setEnvironmentalScore(PortfolioUtilities.normaliseDecimals(portfolio.getSustainalyticsEnvScore()));
                benchMarkResponseDto.setSocialScore(PortfolioUtilities.normaliseDecimals(portfolio.getSustainalyticsSocialScore()));
                benchMarkResponseDto.setGovernanceScore(PortfolioUtilities.normaliseDecimals(portfolio.getSustainalyticsGovScore()));
                benchMarkResponseDto.setBenchmarkdata(dataStore.get(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE));

                for (Benchmark benchmarkDataLakeResponse : dataStore.get(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE)) {
                    Double cumulativeESGScore = ((portfolio.getSustainalyticsCombinedScore()
                            - benchmarkDataLakeResponse.getIndexTotalScore()) /
                            benchmarkDataLakeResponse.getIndexTotalScore()) * 100;

                    Double cumulativeEnvScore = ((portfolio.getSustainalyticsEnvScore()
                            - benchmarkDataLakeResponse.getEnvIndexScore()) /
                            benchmarkDataLakeResponse.getEnvIndexScore()) * 100;

                    Double cumulativeSocialScore = ((portfolio.getSustainalyticsSocialScore()
                            - benchmarkDataLakeResponse.getSocialIndexScore()) /
                            benchmarkDataLakeResponse.getSocialIndexScore()) * 100;

                    Double cumulativeGovScore = ((portfolio.getSustainalyticsGovScore()
                            - benchmarkDataLakeResponse.getGovIndexScore()) /
                            benchmarkDataLakeResponse.getGovIndexScore()) * 100;

                    cumulativeBenchmarks.add(new CumulativeBenchmark(benchmarkDataLakeResponse.getIndexName(),
                            PortfolioUtils.formatDouble(cumulativeESGScore), PortfolioUtils.formatDouble(cumulativeEnvScore),
                            PortfolioUtils.formatDouble(cumulativeSocialScore), PortfolioUtils.formatDouble(cumulativeGovScore)));
                }
            }
            cumulativeBenchmarks.sort(Comparator.comparing(CumulativeBenchmark::getIndexName));
            benchMarkResponseDto.setCumulativeBenchmarks(cumulativeBenchmarks);
        }else{
            benchMarkResponseDto = null;
        }

        return benchMarkResponseDto;
    }

    @Override
    public FundsBenchmarkResponseDto getFundBenchmarksForPortfolio(String portfolioId) throws IOException {

        FundsBenchmarkResponseDto fundsBenchmarkResponseDto = new FundsBenchmarkResponseDto();
        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        if(applicationComponentSettingsMap.get(Constant.IS_BENCHMARK_ENABLED).getValue().equals(Boolean.TRUE)) {

            IMap<String, List<Benchmark>> dataStore = hazelcastInstance.getMap("benchmarks");
            IMap<String, Portfolio> dataStorePortfolio = hazelcastInstance.getMap("portfolios");
            Portfolio portfolio = dataStorePortfolio.get(portfolioId);

            List<CumulativeFundsBenchmark> cumulativeBenchmarks = new ArrayList<>();
            List<Benchmark> msciBenchmarks = new ArrayList<>();

            //Getting data from data lake
            ResponseEntity<List<BenchmarkDataLakeResponse>> benmarkListResponseEntity = benchmarkServiceProxy.getBenchmark();
            if (benmarkListResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                if (benmarkListResponseEntity.getBody() == null) {
                    log.error("Error occurred while retrieving benchmark data from benchmark service or received null data.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while retrieving benchmark data from benchmark service or received null data.");
                }
            }

            //Storing data to cache
            dataStore.evictAll();

            List<BenchmarkDataLakeResponse> benchmarkDataLakeResponses = benmarkListResponseEntity.getBody().stream()
                    .filter(benchmarkDataLakeResponse -> Constant.DS3.equalsIgnoreCase(benchmarkDataLakeResponse.getSource())).collect(Collectors.toList());

            for (BenchmarkDataLakeResponse benchmarkDataLakeResponse : benchmarkDataLakeResponses) {
                if (Constant.DS3.equalsIgnoreCase(benchmarkDataLakeResponse.getSource())) {
                    msciBenchmarks.add(new Benchmark(benchmarkDataLakeResponse.getIndexName(), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getIndexTotalScore()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getEnvIndexScore()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getSocialIndexScore()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getGovIndexScore()),
                            benchmarkDataLakeResponse.getEsgMsciScore(), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getFdNr()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getFdA()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getFdAA()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getFdAAA()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getFdB()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getFdBB()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getFdBBB()), PortfolioUtilities.normaliseDecimals(benchmarkDataLakeResponse.getFdCCC())));
                } else {
                    log.error("Invalid data source.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
                }
            }
            dataStore.put(Constant.MSCI_INVESTIBLE_UNVIERSE_TYPE, msciBenchmarks);

            //Set value in response
            fundsBenchmarkResponseDto.setPortfolioId(portfolioId);
            fundsBenchmarkResponseDto.setPortfolioName(portfolio.getPortfolioName());

            if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.MSCI_INVESTIBLE_UNVIERSE_TYPE)) {
                fundsBenchmarkResponseDto.setTotalESG(PortfolioUtilities.normaliseDecimals(portfolio.getFundEsgScore()));
                fundsBenchmarkResponseDto.setEsgMsciScore(portfolio.getEsgMsciScore());
                fundsBenchmarkResponseDto.setFdNr(portfolio.getFdNr());
                fundsBenchmarkResponseDto.setFdA(portfolio.getFdA());
                fundsBenchmarkResponseDto.setFdAA(portfolio.getFdAA());
                fundsBenchmarkResponseDto.setFdAAA(portfolio.getFdAAA());
                fundsBenchmarkResponseDto.setFdB(portfolio.getFdB());
                fundsBenchmarkResponseDto.setFdBB(portfolio.getFdBB());
                fundsBenchmarkResponseDto.setFdBBB(portfolio.getFdBBB());
                fundsBenchmarkResponseDto.setFdCCC(portfolio.getFdCCC());
                fundsBenchmarkResponseDto.setBenchmarkdata(dataStore.get(Constant.MSCI_INVESTIBLE_UNVIERSE_TYPE));

                for (Benchmark benchmarkDataLakeResponse : dataStore.get(Constant.MSCI_INVESTIBLE_UNVIERSE_TYPE)) {
                    Double cumulativeESGScore = ((portfolio.getFundEsgScore()
                            - benchmarkDataLakeResponse.getIndexTotalScore()) /
                            benchmarkDataLakeResponse.getIndexTotalScore()) * 100;

                    cumulativeBenchmarks.add(new CumulativeFundsBenchmark(
                            benchmarkDataLakeResponse.getIndexName(),
                            PortfolioUtilities.normaliseDecimals(cumulativeESGScore)));
                }
            }
            cumulativeBenchmarks.sort(Comparator.comparing(CumulativeFundsBenchmark::getIndexName));
            fundsBenchmarkResponseDto.setCumulativeBenchmarks(cumulativeBenchmarks);
        }else{
            fundsBenchmarkResponseDto = null;
        }
        return fundsBenchmarkResponseDto;
    }
}
