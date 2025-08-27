package org.synechron.portfolio.service;

import org.synechron.portfolio.response.dto.HeatMapResponseDto;
import org.synechron.portfolio.response.dto.NewFeedsResponseDto;
import java.io.IOException;
import java.util.Map;

public interface CompanyNewsService {

	HeatMapResponseDto getCompanyHeatMap(String isin);

	NewFeedsResponseDto getCompanyNewsFeeds(String isin);

	Map<String, Double> getNewsSentimentScore(String isin) throws IOException;
}

