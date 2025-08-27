package org.synechron.portfolio.service;

import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.response.dto.ESGScore;
import org.synechron.portfolio.response.dto.RestResponse;
import org.synechron.portfolio.response.history.HistoryResponse;
import java.io.IOException;

public interface CalculationService {

	public RestResponse calculateESGforPortfolio(Portfolio portfolio) throws Exception;

	public HistoryResponse getHistoricalData(String portfolioId, String responseType) throws IOException;

	public Double getPortfolioInsights(Portfolio portfolio);

	public ESGScore getSingleComponentInsights(Portfolio portfolio);
}
