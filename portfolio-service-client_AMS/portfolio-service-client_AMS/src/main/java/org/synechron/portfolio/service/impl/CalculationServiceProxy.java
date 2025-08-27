package org.synechron.portfolio.service.impl;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.synechron.esg.model.Portfolio;
import org.synechron.esg.model.PortfolioHistoryResponse;
import org.synechron.portfolio.response.dto.ESGScore;

@FeignClient(fallback = CalculationServiceProxyFallback.class, url = "${calculation.url}", value = "calculation")
public interface CalculationServiceProxy {

	@RequestMapping(value = "/calculation/calculate", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<Portfolio> calculate(@RequestBody Portfolio portfolio);

	@RequestMapping(value = "/calculation/portfolioPerformance/", method = RequestMethod.POST, produces = {"application/json" })
	public ResponseEntity<List<PortfolioHistoryResponse>> getHistoricalData(@RequestBody Portfolio portfolio);

	@RequestMapping(value = "/calculation/portfolioInsights/", method = RequestMethod.POST, produces = {"application/json" })
	public ResponseEntity<Double> getPortfolioInsights(@RequestBody Portfolio portfolio);

	@RequestMapping(value = "/calculation/singleComponentPortfolioInsights/", method = RequestMethod.POST, produces = {"application/json" })
	public ResponseEntity<ESGScore> getSingleComponentInsights(Portfolio portfolio);

}
