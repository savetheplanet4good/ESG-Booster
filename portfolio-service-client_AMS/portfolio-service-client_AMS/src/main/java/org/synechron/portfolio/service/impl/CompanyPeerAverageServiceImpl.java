package org.synechron.portfolio.service.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.model.ApplicationComponentSettings;
import org.synechron.esg.model.Company;
import org.synechron.esg.model.CompanyESGScore;
import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.application_settings.service.ApplicationComponentSettingsService;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.response.dto.*;
import org.synechron.portfolio.service.CompanyPeerAverageService;
import org.synechron.portfolio.utils.PortfolioUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyPeerAverageServiceImpl implements CompanyPeerAverageService {

    @Autowired
    private DataLakeServiceProxy dataLakeProxy;

    @Autowired
    HazelcastInstance hazelcastInstance;

    @Autowired
    private ApplicationComponentSettingsService applicationComponentSettingsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyPeerAverageServiceImpl.class);

    @Override
    public CompanyPeerAverageUIResponseDto getPeerAverageData(String portfolioId, String isin) throws IOException {

        CompanyPeerAverageUIResponseDto companyPeerAverageUIResponseDto = new CompanyPeerAverageUIResponseDto();
        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap = applicationComponentSettingsService.getInitialApplicationComponentSettings();

        if (applicationComponentSettingsMap.get(Constant.IS_PEER_COMPARISON_ENABLED).getValue().equals(Boolean.TRUE)) {

            List<CompanyPeerAverageScore> companyPeerAverageListDatalakeResponse = new ArrayList<>();

            ResponseEntity<List<CompanyPeerAverageScore>> dataLakeresponse = dataLakeProxy.getCompanyPeerAverageScore(isin);
            if (dataLakeresponse.getStatusCode().equals(HttpStatus.OK) && dataLakeresponse.getBody() != null) {
                LOGGER.info("Datalake Response : " + dataLakeresponse.getBody());
                companyPeerAverageListDatalakeResponse = dataLakeresponse.getBody();
            } else {
                LOGGER.error("Error occurred while retrieving investable universe data from datalake service or received null data.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while retrieving investable universe data from datalake service or received null data.");
            }

            companyPeerAverageUIResponseDto = convertToUIResponse(isin, portfolioId, companyPeerAverageListDatalakeResponse);
        }
        LOGGER.info("Final Response : " + companyPeerAverageUIResponseDto);
        return companyPeerAverageUIResponseDto;
    }

    public CompanyPeerAverageUIResponseDto convertToUIResponse(String isin, String portfolioId, List<CompanyPeerAverageScore> companyPeerAverageScoreList) throws IOException {

        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap = applicationComponentSettingsService.getInitialApplicationComponentSettings();

        List<CompanyPeerAverageDto> companies = new ArrayList<>();
        List<String> isinList = new ArrayList<>();

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");

        if(StringUtils.isNotEmpty(portfolioId)){
            Portfolio portfolio = dataStore.get(portfolioId);
            isinList = PortfolioUtils.getISINList(portfolio);
        }

        for (CompanyPeerAverageScore companyPeerAverageScore : companyPeerAverageScoreList) {
            CompanyPeerAverageDto companyPeerAverageDto = new CompanyPeerAverageDto();

            companyPeerAverageDto.setIsin(companyPeerAverageScore.getIsin());
            companyPeerAverageDto.setCompanyName(companyPeerAverageScore.getCompanyName());
            companyPeerAverageDto.setSector(companyPeerAverageScore.getSector());
            companyPeerAverageDto.setIndustryName(companyPeerAverageScore.getIndustryCategory());
            companyPeerAverageDto.setIsPortfolioCompany(StringUtils.isEmpty(portfolioId) ? Boolean.FALSE : isinList.contains(companyPeerAverageScore.getIsin()));
            companyPeerAverageDto.setIsOutLier(
                    applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue() &&
                    companyPeerAverageScore.getEsgOutlier() != null &&
                    companyPeerAverageScore.getEsgOutlier() > Constant.OUTLIER_PERCENTILE ? Boolean.TRUE : Boolean.FALSE);

            //DS1
            CompanyPeerAverageScores companyPeerAverageScoresDS1 = new CompanyPeerAverageScores(
                    PortfolioUtilities.normaliseDecimals(companyPeerAverageScore.getTotalEsg()),
                    companyPeerAverageScore.getRankPercentile(),
                    companyPeerAverageScore.getEsgOutlier(),
                    companyPeerAverageScore.getRank()
            );
            companyPeerAverageDto.setDs1Scores(companyPeerAverageScoresDS1);

            //DS2
            CompanyPeerAverageScores companyPeerAverageScoresDS2 = new CompanyPeerAverageScores(
                    PortfolioUtilities.normaliseDecimals(companyPeerAverageScore.getTotalEsgDs2()),
                    companyPeerAverageScore.getRankPercentileDs2(),
                    companyPeerAverageScore.getEsgOutlierDs2(),
                    companyPeerAverageScore.getRankDs2()
            );
            companyPeerAverageDto.setDs2Scores(companyPeerAverageScoresDS2);

            //Add object to list
            companies.add(companyPeerAverageDto);
        }

        //Filter companies top 10 with respect to selected company
        companies = filterCompanies(isin, companies);

        CompanyPeerAverageUIResponseDto companyPeerAverageUIResponseDto = new CompanyPeerAverageUIResponseDto(
                companyPeerAverageScoreList.get(0).getIndustryCategory(),
                PortfolioUtilities.normaliseDecimals(companyPeerAverageScoreList.get(0).getAvgEsg()),
                PortfolioUtilities.normaliseDecimals(companyPeerAverageScoreList.get(0).getAvgEsgDs2()),
                PortfolioUtilities.normaliseDecimals(companyPeerAverageScoreList.get(0).getBestEsg()),
                PortfolioUtilities.normaliseDecimals(companyPeerAverageScoreList.get(0).getBestEsgDs2()),
                companies
        );
        return companyPeerAverageUIResponseDto;
    }

    public List<CompanyPeerAverageDto> filterCompanies(String isin, List<CompanyPeerAverageDto> companyPeerAverageDtos) throws IOException {

        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap = applicationComponentSettingsService.getInitialApplicationComponentSettings();

        List<CompanyPeerAverageDto> filteredCompanies = new ArrayList<>();

        //Fetch portfolio companies
        List<CompanyPeerAverageDto> portfolioCompanies = companyPeerAverageDtos.stream().filter(companyPeerAverageDto -> companyPeerAverageDto.getIsPortfolioCompany().equals(Boolean.TRUE)).collect(Collectors.toList());
        filteredCompanies.addAll(portfolioCompanies);

        if (applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE)) {
            List<CompanyPeerAverageDto> remainingCompanies = companyPeerAverageDtos.stream().filter(companyPeerAverageDto -> companyPeerAverageDto.getIsPortfolioCompany().equals(Boolean.FALSE)).collect(Collectors.toList());

            //Fetch companies with high outlier score
            List<CompanyPeerAverageDto> outlierCompanies = remainingCompanies.stream().filter(
                    companyPeerAverageDto -> companyPeerAverageDto.getIsOutLier().equals(Boolean.TRUE)
            ).collect(Collectors.toList());
            filteredCompanies.addAll(outlierCompanies);

            //in case if outlier is less than 4 , add the scores below the par score to make it atleast four points
            if (outlierCompanies != null && outlierCompanies.size() < 4) {

                // add the other outliers may be less than par to make it an set of 4 outlier Gradients
                remainingCompanies.removeAll(outlierCompanies);

                Comparator<CompanyPeerAverageDto> companyPeerAverageDtoComparator = Comparator.comparing((CompanyPeerAverageDto o) -> o.getDs1Scores().getOutlierScore()).reversed();
                Collections.sort(remainingCompanies, companyPeerAverageDtoComparator);

                // Get the remnant number that when added will make outliers set as 4
                int outlierFillerSize = 4 - outlierCompanies.size();
                List<CompanyPeerAverageDto> fillerOutliers = remainingCompanies.stream().limit(outlierFillerSize).collect(Collectors.toList());
                fillerOutliers.stream().forEach(f -> f.setIsOutLier(Boolean.TRUE));
                filteredCompanies.addAll(fillerOutliers);
            }
        }
        return filteredCompanies;
    }

}

