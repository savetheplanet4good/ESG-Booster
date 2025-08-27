package org.synechron.news.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synechron.news.request.NewsSentimentRequest;
import org.synechron.news.response.HeatMapResponseDto;
import org.synechron.news.response.NewFeedsResponseDto;
import org.synechron.news.response.NewsDto;
import org.synechron.news.response.NewsFeedsDto;
import org.synechron.news.service.CompanyNewsService;
import org.synechron.news.service.impl.CompanyNewsServiceImpl;

/**
 *
 * Class Name : CompanyNewsController
 * Purpose : The class is a news controller where we have all the endpoints related to news.
 *
 */
@CrossOrigin
@RestController
public class CompanyNewsController {

    /**
     * Autowiring of News Service to access the services
     */
    @Autowired
    CompanyNewsService companyNewsService;

    /**
     * Class logger.
     */
    private static final Logger log = LoggerFactory.getLogger(CompanyNewsController.class);

    /**
     *
     * Function Name : getHeatMapDataForCompany()
     * Purpose : Retrieve the news heat map data for a specific company based on company isin passed.
     * @param isin : Company ISIN for which we require the news heat map data.
     * @return : HeatMapResponseDto object in form of response entity.
     *
     */
    @GetMapping(value = "/company-heat-map/{isin}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHeatMapDataForCompany(@PathVariable String isin){
        try {
            HeatMapResponseDto heatMapeResponseDtos = companyNewsService.getHeatMapDataForCompany(isin);
            return new ResponseEntity<>(heatMapeResponseDtos, getNoCacheHeaders(), HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception message : " + e.getMessage());
            log.error("Error Occured while retrieving Heat Map data from Datalake");
            return new ResponseEntity<>("Error Occured while retrieving Heat Map data from Datalake", getNoCacheHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * Function Name : getCompanyNewsFeeds()
     * Purpose : Retrieve the news feeds data for a specific company based on company isin passed.
     * @param isin : Company ISIN for which we require the news feeds.
     * @return : NewFeedsResponseDto object in form of response entity.
     *
     */
    @PostMapping(value = "/company-news-feeds", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCompanyNewsFeeds(@RequestBody String isin){
        try {
            NewFeedsResponseDto newsFeedsResponseDtos = companyNewsService.getCompanyNewsFeeds(isin);
            return new ResponseEntity<>(newsFeedsResponseDtos, getNoCacheHeaders(), HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception message : " + e.getMessage());
            log.error("Error Occured while retrieving News feeds data from Datalake");
            return new ResponseEntity<>("Error Occured while retrieving News feeds data from Datalake", getNoCacheHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * Function Name : getLatest()
     * Purpose : Retrieve the latest news data for companies based on company isins list passed.
     * @param isin : Company ISIN list for which we require the news feeds.
     * @return : Map<String, NewsDto> object in form of response entity.
     *
     */
    @PostMapping(value="/latest/news",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLatest(@RequestBody List<String> isin){
        try {
            Map<String, NewsDto> newsDtoMap = companyNewsService.getLatestNews(isin);
            return new ResponseEntity<>(newsDtoMap, getNoCacheHeaders(), HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception message : " + e.getMessage());
            log.error("Error Occured while retrieving latest news data from Datalake");
            return new ResponseEntity<>("Error Occured while retrieving latest news data from Datalake", getNoCacheHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * Function Name : getNewsSentimentScore()
     * Purpose : Retrieve the news sentiment data for companies based on company isins list passed in input.
     * @param newsSentimentRequest : NewsSentimentRequest object in form of requestBody
     * @return : List<NewsSentimentsDto> object in form of response entity.
     *
     */
    @PostMapping(value="/sentiment-score",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNewsSentimentScore(@RequestBody NewsSentimentRequest newsSentimentRequest){
        try {
            Map<String, Double> newsSentimentsMap = companyNewsService.getNewsSentimentScore(newsSentimentRequest);
            return new ResponseEntity<>(newsSentimentsMap, getNoCacheHeaders(), HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception message : " + e.getMessage());
            log.error("Error Occured while retrieving news sentiment data from Datalake");
            return new ResponseEntity<>("Error Occured while retrieving news sentiment data from Datalake", getNoCacheHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
    *
    * Function Name : getPortfolioNewsFeeds()
     * Purpose : Retrieve the latest three news data for portfolio based on company isins list passed.
     * @param isin : Company ISIN list for which we require the news feeds.
     * @return : List<NewsDto> object in form of response entity.
    *
    */
    @PostMapping(value="/portfolio-news-feeds",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPortfolioNewsFeeds(@RequestBody List<String> isin){
        try {
        	List<NewsFeedsDto> newsFeedsResponse = companyNewsService.getPortfolioNewsFeeds(isin);
            return new ResponseEntity<>(newsFeedsResponse, getNoCacheHeaders(), HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception message : " + e.getMessage());
            log.error("Error Occured while retrieving latest news data from Datalake");
            return new ResponseEntity<>("Error Occured while retrieving latest news data from Datalake", getNoCacheHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

	@PostMapping(value = "/portfolio-news-feeds/count/{count}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPortfolioCountNewsFeeds(@RequestBody List<String> isin,
			@PathVariable Integer count) {
		try {
				List<NewsFeedsDto> newsFeedsResponse = companyNewsService.getPortfolioCountNewsFeeds(isin, count);
				return new ResponseEntity<>(newsFeedsResponse, getNoCacheHeaders(), HttpStatus.OK);
			 
		} catch (Exception e) {
            log.error("Exception message : " + e.getMessage());
            log.error("Error Occured while retrieving latest news data from Datalake");
			return new ResponseEntity<>("Error Occured while retrieving latest news data from Datalake",
					getNoCacheHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

    
    private HttpHeaders getNoCacheHeaders() {
        HttpHeaders responseHeaders=new HttpHeaders();
        responseHeaders.set("Cache-Control","no-cache");
        return responseHeaders;
    }
}
