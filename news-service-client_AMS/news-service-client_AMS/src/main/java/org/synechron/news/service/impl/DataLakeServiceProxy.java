package org.synechron.news.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.synechron.news.request.NewsSentimentRequest;
import org.synechron.news.response.HeatMapDataLakeResponseDto;
import org.synechron.news.response.NewsDataLakeResponseDto;
import org.synechron.news.response.NewsDto;
import org.synechron.news.response.NewsFeedsDto;
import org.synechron.news.response.NewsSentimentsDto;

/**
 *
 * Interface Name : DataLakeServiceProxy Purpose : Proxy created to communicate
 * with the datalake using feign client.
 *
 */
@FeignClient(fallback = DataLakeServiceProxyFallback.class, url = "${datalake.url}", value = "dl")
public interface DataLakeServiceProxy {

	/**
	 *
	 * Function Name : getHeatMapDataForCompany() Purpose : Proxy datalake endpoint.
	 * 
	 * @param isin : String object in form of RequestBody
	 * @return : List<HeatMapDataLakeResponseDto> object in form of response entity.
	 *
	 */
	@RequestMapping(value = "/datalake/company-heat-map", method = RequestMethod.POST, produces = {
			"application/json" })
	public ResponseEntity<List<HeatMapDataLakeResponseDto>> getHeatMapDataForCompany(@RequestBody String isin);

	/**
	 *
	 * Function Name : getCompanyNewsFeeds() Purpose : Proxy datalake endpoint.
	 * 
	 * @param isin : String object in form of RequestBody
	 * @return : List<NewsDataLakeResponseDto> object in form of response entity.
	 *
	 */
	@RequestMapping(value = "/datalake/company-news-feeds", method = RequestMethod.POST, produces = {
			"application/json" })
	public ResponseEntity<List<NewsDataLakeResponseDto>> getCompanyNewsFeeds(@RequestBody String isin);

	/**
	 *
	 * Function Name : getNews() Purpose : Proxy datalake endpoint.
	 * 
	 * @param isin : String object in form of PathVariable
	 * @return : List<NewsDto> object in form of response entity.
	 *
	 */
	@RequestMapping(value = "/datalake/company-news-feeds/{isin}?limit=1", method = RequestMethod.GET, produces = {
			"application/json" })
	public ResponseEntity<List<NewsDto>> getNews(@PathVariable(value = "isin") String isin);

	/**
	 *
	 * Function Name : getLatestNews() Purpose : Proxy datalake endpoint.
	 * 
	 * @param isin : String object in form of RequestBody
	 * @return : Map<String, NewsDto> object in form of response entity.
	 *
	 */
	@RequestMapping(value = "/datalake/latest/news", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<Map<String, NewsDto>> getLatestNews(@RequestBody List<String> isin);

	/**
	 *
	 * Function Name : getNewsSentimentScore() Purpose : Return error in case of
	 * failure.
	 * 
	 * @param newsSentimentRequest : NewsSentimentRequest object in form of
	 *                             RequestBody
	 * @return : List<NewsSentimentsDto> object in form of response entity.
	 *
	 */
	@RequestMapping(value = "/datalake/news-sentiment-score", method = RequestMethod.POST, produces = {
			"application/json" })
	public ResponseEntity<List<NewsSentimentsDto>> getNewsSentimentScore(
			@RequestBody NewsSentimentRequest newsSentimentRequest);

	/**
	 *
	 * Function Name : getLatestNews() Purpose : Proxy datalake endpoint.
	 * 
	 * @param isin : String object in form of RequestBody
	 * @return : Map<String, NewsDto> object in form of response entity.
	 *
	 */
	@RequestMapping(value = "/datalake/portfolio/news", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<List<NewsFeedsDto>> getPortfolioNews(@RequestBody List<String> isin);

	@RequestMapping(value = "/datalake/portfolio/news/count/{count}", method = RequestMethod.POST, produces = {
			"application/json" })
	public ResponseEntity<List<NewsFeedsDto>> getPortfolioCountNewsFeeds(@RequestBody List<String> isin,
			@PathVariable Integer count);

}
