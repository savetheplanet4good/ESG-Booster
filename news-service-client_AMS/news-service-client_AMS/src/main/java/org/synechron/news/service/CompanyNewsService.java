package org.synechron.news.service;

import org.synechron.news.request.NewsSentimentRequest;
import org.synechron.news.response.*;

import java.util.List;
import java.util.Map;

/**
 *
 * Interface Name : CompanyNewsService
 * Purpose : This interface will contain all the services that can be accessed from the controller.
 *
 */
public interface CompanyNewsService {

    /**
     *
     * Function Name : getHeatMapDataForCompany()
     * Purpose : Retrieve the news heat map data.
     *
     */
    public HeatMapResponseDto getHeatMapDataForCompany(String isin);

    /**
     *
     * Function Name : getCompanyNewsFeeds()
     * Purpose : Retrieve the news feeds data, top 5.
     *
     */
    public NewFeedsResponseDto getCompanyNewsFeeds(String isin);

    /**
     *
     * Function Name : getLatestNews()
     * Purpose : Retrieve the top news article data.
     *
     */
    public Map<String, NewsDto> getLatestNews(List<String> isin);

    /**
     *
     * Function Name : getNewsSentimentScore()
     * Purpose : Retrieve news sentiment score.
     *
     */
    public Map<String, Double> getNewsSentimentScore(NewsSentimentRequest newsSentimentRequest);
    
    /**
    *
    * Function Name : getPortfolioNewsFeeds()
    * Purpose : Retrieve the news feeds data, top 3.
    *
    */
   public List<NewsFeedsDto> getPortfolioNewsFeeds(List<String> isin);
   
   public List<NewsFeedsDto> getPortfolioCountNewsFeeds(List<String> isin, Integer count);

}
