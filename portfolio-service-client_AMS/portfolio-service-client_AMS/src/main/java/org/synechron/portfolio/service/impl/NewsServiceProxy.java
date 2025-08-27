package org.synechron.portfolio.service.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.synechron.esg.model.NewsDto;
import org.synechron.portfolio.request.NewsSentimentRequest;
import org.synechron.portfolio.response.NewsSentimentsDto;
import org.synechron.portfolio.response.dto.HeatMapResponseDto;
import org.synechron.portfolio.response.dto.NewFeedsResponseDto;

import java.util.List;
import java.util.Map;

@FeignClient(fallback = NewsServiceProxyFallback.class, url = "${news.url}", value = "news")
public interface NewsServiceProxy {

    @RequestMapping(value = "/news/company-heat-map/{isin}", method = RequestMethod.GET, produces = {"application/json"})
    public ResponseEntity<HeatMapResponseDto> getHeatMapDataForCompany(@PathVariable String isin);

    @RequestMapping(value = "/news/company-news-feeds", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<NewFeedsResponseDto> getCompanyNewsFeeds(@RequestBody String isin);

    @RequestMapping(value = "/news/latest/news", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<Map<String, NewsDto>> getLatestNews(@RequestBody List<String> isin);

    @RequestMapping(value = "/news/sentiment-score", method = RequestMethod.POST, produces = {"application/json"})
    public ResponseEntity<Map<String, Double>> getNewsSentimentScore(@RequestBody NewsSentimentRequest newsSentimentRequest);

}
