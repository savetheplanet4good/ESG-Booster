package org.synechron.news.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.synechron.news.request.NewsSentimentRequest;
import org.synechron.news.response.HeatMapDataLakeResponseDto;
import org.synechron.news.response.NewsDataLakeResponseDto;
import org.synechron.news.response.NewsDto;
import org.synechron.news.response.NewsFeedsDto;
import org.synechron.news.response.NewsSentimentsDto;

/**
 *
 * Class Name : DataLakeServiceProxyFallback Purpose : This class handle
 * fallback in case of failure request.
 *
 */
@Service
public class DataLakeServiceProxyFallback implements DataLakeServiceProxy {

	/**
	 *
	 * Function Name : getHeatMapDataForCompany() Purpose : Return error in case of
	 * failure.
	 * 
	 * @param isin : String object in form of RequestBody
	 * @return : List<HeatMapDataLakeResponseDto> object in form of response entity.
	 *
	 */
	@Override
	public ResponseEntity<List<HeatMapDataLakeResponseDto>> getHeatMapDataForCompany(String isin) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 *
	 * Function Name : getCompanyNewsFeeds() Purpose : Return error in case of
	 * failure.
	 * 
	 * @param isin : String object in form of RequestBody
	 * @return : List<NewsDataLakeResponseDto> object in form of response entity.
	 *
	 */
	@Override
	public ResponseEntity<List<NewsDataLakeResponseDto>> getCompanyNewsFeeds(String isin) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 *
	 * Function Name : getNews() Purpose : Return error in case of failure.
	 * 
	 * @param isin : String object in form of PathVariable
	 * @return : List<NewsDto> object in form of response entity.
	 *
	 */
	@Override
	public ResponseEntity<List<NewsDto>> getNews(String isinCodes) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 *
	 * Function Name : getLatestNews() Purpose : Return error in case of failure.
	 * 
	 * @param isin : String object in form of RequestBody
	 * @return : Map<String, NewsDto> object in form of response entity.
	 *
	 */
	@Override
	public ResponseEntity<Map<String, NewsDto>> getLatestNews(List<String> isin) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 *
	 * Function Name : getNewsSentimentScore() Purpose : Return error in case of
	 * failure.
	 * 
	 * @param newsSentimentRequest : NewsSentimentRequest object form of RequestBody
	 * @return : List<NewsSentimentsDto> object in form of response entity.
	 *
	 */
	@Override
	public ResponseEntity<List<NewsSentimentsDto>> getNewsSentimentScore(NewsSentimentRequest newsSentimentRequest) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<NewsFeedsDto>> getPortfolioNews(List<String> isin) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<List<NewsFeedsDto>> getPortfolioCountNewsFeeds(List<String> isin, Integer count) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
