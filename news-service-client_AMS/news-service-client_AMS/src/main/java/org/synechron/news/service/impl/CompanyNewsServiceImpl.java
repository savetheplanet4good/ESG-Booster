package org.synechron.news.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.news.constants.Constants;
import org.synechron.news.request.NewsSentimentRequest;
import org.synechron.news.response.HeatMapDataLakeResponseDto;
import org.synechron.news.response.HeatMapResponseDto;
import org.synechron.news.response.HeatMapUIResponseDto;
import org.synechron.news.response.NewFeedsResponseDto;
import org.synechron.news.response.NewsDataLakeResponseDto;
import org.synechron.news.response.NewsDto;
import org.synechron.news.response.NewsFeedsDto;
import org.synechron.news.response.NewsSentimentsDto;
import org.synechron.news.service.CompanyNewsService;
import org.synechron.news.utils.NewsUtilities;

/**
 *
 * Class Name : CompanyNewsServiceImpl
 * Purpose : This class is a contains the business logic for the CompanyNewsService service methods, basically the service implemention code.
 *
 */
@Service
public class CompanyNewsServiceImpl implements CompanyNewsService {

    /**
     * Class logger.
     */
    private static final Logger log = LoggerFactory.getLogger(CompanyNewsServiceImpl.class);

    /**
     * Autowiring of DataLakeServiceProxy to communicate with datalake services.
     */
    @Autowired
    DataLakeServiceProxy dataLakeServiceProxy;

    /**
     *
     * Function Name : getHeatMapDataForCompany()
     * Purpose : Retrieve the news heat map data by calling the datalake service.
     *
     */
    @Override
    public HeatMapResponseDto getHeatMapDataForCompany(String isin) {

        log.info("ISIN : " + isin);
        HeatMapResponseDto heatMapResponseDto = new HeatMapResponseDto();

        ResponseEntity<List<HeatMapDataLakeResponseDto>> heatMapDataLakeResponseDto = dataLakeServiceProxy.getHeatMapDataForCompany(isin);
        log.info("Datalake response : " + heatMapDataLakeResponseDto.getBody().toString());

        if (heatMapDataLakeResponseDto.getStatusCode().equals(HttpStatus.OK)) {
            if(!heatMapDataLakeResponseDto.getBody().isEmpty()){
                heatMapResponseDto = convertToResponseDto(heatMapDataLakeResponseDto.getBody());
                log.info("Final API response : " + heatMapResponseDto );
            }
        } else {
            log.error("Error occurred while retrieving heat map data from data lake or Empty data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while retrieving heat map data from data lake or Empty data.");
        }
        return heatMapResponseDto;
    }

    /**
     *
     * Function Name : convertToResponseDto()
     * Purpose : Convert the result set fetched from datalake into json format that will be direct input to the heat map UI component.
     *
     */
    private HeatMapResponseDto convertToResponseDto(List<HeatMapDataLakeResponseDto> heatMapDataLakeResponseDtos) {

        Double averageSentimentScore = 0.0;
        HeatMapResponseDto heatMapResponseDto = new HeatMapResponseDto();
        List<HeatMapUIResponseDto> finalList = new ArrayList<>();

        //level1
        List<HeatMapUIResponseDto> level1_topics = heatMapDataLakeResponseDtos.stream().filter(h -> h.getParent().isEmpty())
                .map(ui -> new HeatMapUIResponseDto(
                            NewsUtilities.getRandomStringId(),
                                ui.getValue(),
                                ui.getParent(),
                                NewsUtilities.normaliseDecimals(ui.getSentimentScore()),
                                ui.getNoOfArticles()
                        )
                ).collect(Collectors.toList());


        if(level1_topics.size() > 0) {
            finalList.addAll(level1_topics);
            averageSentimentScore = finalList.stream().mapToDouble(HeatMapUIResponseDto::getSentimentScore).average().getAsDouble();

            //level2
            for (HeatMapUIResponseDto topics : level1_topics) {
                List<HeatMapDataLakeResponseDto> filterTop10Groups = heatMapDataLakeResponseDtos.stream().filter(f -> Constants.GROUP_PREFIX.concat(topics.getName()).equals(f.getParent())).sorted(Comparator.comparing(HeatMapDataLakeResponseDto::getNoOfArticles).reversed()).limit(Constants.MAXIMUM_RECORD_LIMIT_HEAT_MAP).collect(Collectors.toList());
                List<HeatMapUIResponseDto> level2_groups = filterTop10Groups.stream()
                        .map(ui -> new HeatMapUIResponseDto(
                                NewsUtilities.getRandomStringId(),
                                ui.getValue(),
                                topics.getId(),
                                NewsUtilities.normaliseDecimals(ui.getSentimentScore()),
                                ui.getNoOfArticles()
                        )).collect(Collectors.toList());

                if(level2_groups.size() > 0) {
                    finalList.addAll(level2_groups);

                    //level3
                    for (HeatMapUIResponseDto groups : level2_groups) {
                        List<HeatMapDataLakeResponseDto> filterTop10Types = heatMapDataLakeResponseDtos.stream().filter(f -> Constants.TYPE_PREFIX.concat(groups.getName()).equals(f.getParent())).sorted(Comparator.comparing(HeatMapDataLakeResponseDto::getNoOfArticles).reversed()).limit(Constants.MAXIMUM_RECORD_LIMIT_HEAT_MAP).collect(Collectors.toList());
                        List<HeatMapUIResponseDto> level3_types = filterTop10Types.stream()
                                .map(ui -> new HeatMapUIResponseDto(
                                        NewsUtilities.getRandomStringId(),
                                        ui.getValue(),
                                        groups.getId(),
                                        NewsUtilities.normaliseDecimals(ui.getSentimentScore()),
                                        ui.getNoOfArticles()
                                )).collect(Collectors.toList());

                        if(level3_types.size() > 0) {
                            finalList.addAll(level3_types);

                            //level4
                            for (HeatMapUIResponseDto types : level3_types) {
                                List<HeatMapDataLakeResponseDto> filterTop10Subtypes = heatMapDataLakeResponseDtos.stream().filter(f -> Constants.SUBTYPE_PREFIX.concat(types.getName()).equals(f.getParent())).sorted(Comparator.comparing(HeatMapDataLakeResponseDto::getNoOfArticles).reversed()).limit(Constants.MAXIMUM_RECORD_LIMIT_HEAT_MAP).collect(Collectors.toList());
                                List<HeatMapUIResponseDto> level4_subTypes = filterTop10Subtypes.stream()
                                        .map(ui -> new HeatMapUIResponseDto(
                                                NewsUtilities.getRandomStringId(),
                                                ui.getValue(),
                                                types.getId(),
                                                NewsUtilities.normaliseDecimals(ui.getSentimentScore()),
                                                ui.getNoOfArticles()
                                        )).collect(Collectors.toList());

                                if(level4_subTypes.size() > 0) {
                                    finalList.addAll(level4_subTypes);
                                }
                            }
                        }
                    }
                }
            }
        }

        //Populate Response
        heatMapResponseDto.setAverageSentimentScore(NewsUtilities.normaliseDecimals(averageSentimentScore));
        heatMapResponseDto.setHeatMapData(finalList);

        return heatMapResponseDto;
    }

    /**
     *
     * Function Name : getCompanyNewsFeeds()
     * Purpose : Retrieve the news feeds data, top 5 by calling the datalake service.
     *
     */
    @Override
    public NewFeedsResponseDto getCompanyNewsFeeds(String isin) {

        log.info("ISIN : " + isin);
        List<NewsDataLakeResponseDto> newsDataLakeResponseDtoList = new ArrayList<>();

        ResponseEntity<List<NewsDataLakeResponseDto>> newsDataLakeResponseDtos = dataLakeServiceProxy.getCompanyNewsFeeds(isin);
        log.info("Datalake response : " + newsDataLakeResponseDtos.getBody().toString());

        if (newsDataLakeResponseDtos.getStatusCode().equals(HttpStatus.OK)) {
            if(!newsDataLakeResponseDtos.getBody().isEmpty()){
                newsDataLakeResponseDtoList = newsDataLakeResponseDtos.getBody();
                newsDataLakeResponseDtoList.sort(Comparator.comparing((NewsDataLakeResponseDto :: getTime)).reversed());
                log.info("Reversed response with respect to time : " + newsDataLakeResponseDtoList);
            }
        } else {
            log.error("Error occurred while retrieving news feeds data from data lake or Empty data.");
            throw new ResponseStatusException(newsDataLakeResponseDtos.getStatusCode(), "Error occurred while retrieving news feeds data from data lake or Empty data.");
        }

        //Convert into reponse
        NewFeedsResponseDto newFeedsResponseDto = new NewFeedsResponseDto();
        newFeedsResponseDto.setIsin(isin);
        newFeedsResponseDto.setNewsdata(newsDataLakeResponseDtoList);

        log.info("Final API response : " + newFeedsResponseDto );
        return newFeedsResponseDto;
    }

    /**
     *
     * Function Name : getLatestNews()
     * Purpose : Retrieve the top news article data by calling the datalake service.
     *
     */
    @Override
    public  Map<String, NewsDto> getLatestNews(List<String> companyISIN) {

        log.info("ISINs : " + companyISIN);

        ResponseEntity<Map<String, NewsDto>> newsDtoMapResponseEntity = dataLakeServiceProxy.getLatestNews(companyISIN);
        log.info("Datalake response : " + newsDtoMapResponseEntity.getBody().toString());

        if (newsDtoMapResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if(newsDtoMapResponseEntity.getBody().isEmpty()){
                log.error("Error occurred while retrieving latest news data from data lake or Empty data.");
                throw new ResponseStatusException(newsDtoMapResponseEntity.getStatusCode(), "Error occurred while retrieving latest news data from data lake or Empty data.");
            }
        }

        log.info("Final API response : " + newsDtoMapResponseEntity.getBody().toString() );
        return newsDtoMapResponseEntity.getBody();
    }

    /**
     *
     * Function Name : getNewsSentimentScore()
     * Purpose : Retrieve news sentiment score.
     *
     */
    @Override
    public Map<String, Double> getNewsSentimentScore(NewsSentimentRequest newsSentimentRequest) {

        log.info("News Sentiment Request : " + newsSentimentRequest);

        ResponseEntity<List<NewsSentimentsDto>> newsSentimentsDtoList = dataLakeServiceProxy.getNewsSentimentScore(newsSentimentRequest);
        log.info("Datalake response : " + newsSentimentsDtoList.getBody().toString());

        if (newsSentimentsDtoList.getStatusCode().equals(HttpStatus.OK)) {
            if(newsSentimentsDtoList.getBody().isEmpty()){
                log.error("Error occurred while retrieving news sentiment data from data lake or Empty data.");
                throw new ResponseStatusException(newsSentimentsDtoList.getStatusCode(), "Error occurred while retrieving news sentiment data from data lake or Empty data.");
            }
        }

        //Convert list into map
        Map<String, Double> newsSentimentScoreMap = new HashMap<>();
        for (NewsSentimentsDto newsSentimentsDto : newsSentimentsDtoList.getBody()) {
            newsSentimentScoreMap.put(newsSentimentsDto.getIsin(), NewsUtilities.normaliseDecimals(newsSentimentsDto.getSentimentScore()));
        }

        log.info("Final API response : " + newsSentimentScoreMap );
        return newsSentimentScoreMap;
    }

    /**
    *
    * Function Name : getPortfolioNewsFeeds()
    * Purpose : Retrieve the news feeds data, top 3 by calling the datalake service.
    *
    */
    @Override
    public List<NewsFeedsDto> getPortfolioNewsFeeds(List<String> portISIN) {

        log.info("ISINs : " + portISIN);
        ResponseEntity<List<NewsFeedsDto>> portfolioNewsFeed = dataLakeServiceProxy.getPortfolioNews(portISIN);
        log.info("Datalake response : " + portfolioNewsFeed.getBody().toString());
        List<NewsFeedsDto> response = null;

        if (portfolioNewsFeed.getStatusCode().equals(HttpStatus.OK)) {
            if(portfolioNewsFeed.getBody().isEmpty()){
                log.error("Error occurred while retrieving latest news data from data lake or Empty data.");
                throw new ResponseStatusException(portfolioNewsFeed.getStatusCode(), "Error occurred while retrieving latest news data from data lake or Empty data.");
            }else {
                response = portfolioNewsFeed.getBody().stream().sorted((o1, o2)->o1.getTime().
                        compareTo(o2.getTime())).collect(Collectors.toList());
                Collections.sort(response,Comparator.comparing(NewsFeedsDto::getTime).reversed());
            }
        }
        //response = portfolioNewsFeed.getBody();
        log.info("Final API response : " + portfolioNewsFeed.getBody().toString() );
        return response;
    }
	
	@Override
	public List<NewsFeedsDto> getPortfolioCountNewsFeeds(List<String> isin, Integer count) {

        log.info("ISINs : " + isin + "\n Count : " + count);
		ResponseEntity<List<NewsFeedsDto>> portfolioNewsFeed = dataLakeServiceProxy.getPortfolioCountNewsFeeds(isin,count);
        log.info("Datalake response : " + portfolioNewsFeed.getBody().toString());

		if (portfolioNewsFeed.getStatusCode().equals(HttpStatus.OK)) {
			if (portfolioNewsFeed.getBody().isEmpty()) {
				log.error("Error occurred while retrieving latest news data from data lake or Empty data.");
				throw new ResponseStatusException(portfolioNewsFeed.getStatusCode(),
						"Error occurred while retrieving latest news data from data lake or Empty data.");
			}
		}

        log.info("Final API response : " + portfolioNewsFeed.getBody().toString() );
		return portfolioNewsFeed.getBody();
	}

}
