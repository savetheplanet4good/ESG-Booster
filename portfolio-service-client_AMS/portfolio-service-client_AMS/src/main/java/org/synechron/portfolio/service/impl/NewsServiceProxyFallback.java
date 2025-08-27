package org.synechron.portfolio.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.synechron.esg.model.NewsDto;
import org.synechron.portfolio.request.NewsSentimentRequest;
import org.synechron.portfolio.response.NewsSentimentsDto;
import org.synechron.portfolio.response.dto.HeatMapResponseDto;
import org.synechron.portfolio.response.dto.NewFeedsResponseDto;

import java.util.List;
import java.util.Map;

@Service
public class NewsServiceProxyFallback implements NewsServiceProxy {

    @Override
    public ResponseEntity<HeatMapResponseDto> getHeatMapDataForCompany(String isin) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<NewFeedsResponseDto> getCompanyNewsFeeds(String isin) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Map<String, NewsDto>> getLatestNews(List<String> isin) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Map<String, Double>> getNewsSentimentScore(NewsSentimentRequest newsSentimentRequest) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
