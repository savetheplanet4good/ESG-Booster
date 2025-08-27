package org.synechron.portfolio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.portfolio.response.insights.*;
import org.synechron.portfolio.service.InsightsService;

import java.io.IOException;
import java.util.List;

@RestController
public class InsightsController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(InsightsController.class);

    @Autowired
    private InsightsService insightsService;

    @GetMapping("/portfolio-insights/{portfolioId}")
    public PortfolioInsightsResponseDto getPortfolioInsights(@PathVariable String portfolioId) throws IOException {

        //Percentage change in ESG score
        ESGScorePercentageChangeDto percentageChangeInsight = insightsService.getESGPercentageChange(portfolioId);
    	
		// Single component effect in a portfolio
		SingleComponentEffectDto singleComponentChangeInsight = insightsService.getsingleComponentChange(portfolioId);

        
        //Sectors
        SectorDataResponseDto sectorDataResponseDto = insightsService.getSectorsAndCount(portfolioId);

        //News
        NewsSentimentCountResponseDto newsSentimentCountResponseDto = insightsService.getPositiveNegativeNewsSentimentsCount(portfolioId);

        //Influence
        EsgInfluenceCountResponseDto esgInfluenceCountResponseDto = insightsService.getPositiveNegativeESGInfluenceCount(portfolioId);

        //Outlier
        OutlierRangeResponseDto outlierRangeResponseDto = insightsService.getOutlierRange(portfolioId);

        return new PortfolioInsightsResponseDto(percentageChangeInsight,singleComponentChangeInsight,sectorDataResponseDto, newsSentimentCountResponseDto, esgInfluenceCountResponseDto, outlierRangeResponseDto);
    }

    @GetMapping("/all-portfolio-insights")
    public List<AllPortfolioInsightsResponseDto> getAllPortfolioInsights() throws IOException {
        return insightsService.getAllPortfolioInsights();
    }
}
