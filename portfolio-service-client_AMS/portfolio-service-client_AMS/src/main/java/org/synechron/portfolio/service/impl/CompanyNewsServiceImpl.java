package org.synechron.portfolio.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.model.ApplicationComponentSettings;
import org.synechron.portfolio.application_settings.service.ApplicationComponentSettingsService;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.request.NewsSentimentRequest;
import org.synechron.portfolio.response.dto.HeatMapResponseDto;
import org.synechron.portfolio.response.dto.NewFeedsResponseDto;
import org.synechron.portfolio.service.CompanyNewsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyNewsServiceImpl implements CompanyNewsService {

    private static final Logger log = LoggerFactory.getLogger(CompanyNewsServiceImpl.class);

    @Autowired
    NewsServiceProxy newsServiceProxy;

    @Autowired
    private ApplicationComponentSettingsService applicationComponentSettingsService;

    @Override
    public HeatMapResponseDto getCompanyHeatMap(String isin) {
        ResponseEntity<HeatMapResponseDto> heatMapResponseDto = newsServiceProxy.getHeatMapDataForCompany(isin);
        if (heatMapResponseDto.getStatusCode().equals(HttpStatus.OK)) {
            if(heatMapResponseDto.getBody() == null){
                log.error("Error occurred while retrieving heat map data from news service or received null data.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while retrieving heat map data from news service or received null data.");
            }
        }

        return heatMapResponseDto.getBody();
    }

    @Override
    public NewFeedsResponseDto getCompanyNewsFeeds(String isin) {
        ResponseEntity<NewFeedsResponseDto> newFeedsResponseDtoResponseEntity = newsServiceProxy.getCompanyNewsFeeds(isin);
        if (newFeedsResponseDtoResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if(newFeedsResponseDtoResponseEntity.getBody() == null){
                log.error("Error occurred while retrieving news feeds data from news service or received null data.");
                throw new ResponseStatusException(newFeedsResponseDtoResponseEntity.getStatusCode(), "Error occurred while retrieving news feeds data from news service or received null data.");
            }
        }

        return newFeedsResponseDtoResponseEntity.getBody();
    }

    @Override
    public Map<String, Double> getNewsSentimentScore(String isin) throws IOException {

        Map<String, Double> newsSentimentScoreMap = new HashMap<>();
        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        List<String> isinList = new ArrayList<String>(){
            {
                add(isin);
            }
        };

        if(applicationComponentSettingsMap.get(Constant.IS_NEWS_ENABLED).getValue().equals(Boolean.TRUE)) {
            newsSentimentScoreMap = newsServiceProxy.getNewsSentimentScore(new NewsSentimentRequest(isinList, Constant.NEWS_SENTIMENT_FOR_NO_OF_MONTHS)).getBody();
        }else{
            newsSentimentScoreMap = null;
        }
        return newsSentimentScoreMap;
    }
}

