package org.synechron.portfolio.service;

import org.synechron.portfolio.response.insights.*;
import java.io.IOException;
import java.util.List;

public interface InsightsService {

    public SectorDataResponseDto getSectorsAndCount(String portfolioId);

    public EsgInfluenceCountResponseDto getPositiveNegativeESGInfluenceCount(String portfolioId);

    public NewsSentimentCountResponseDto getPositiveNegativeNewsSentimentsCount(String portfolioId) throws IOException;

    public OutlierRangeResponseDto getOutlierRange(String portfolioId) throws IOException;

    public List<AllPortfolioInsightsResponseDto> getAllPortfolioInsights() throws IOException;

    public ESGScorePercentageChangeDto getESGPercentageChange(String portfolioId) throws IOException;
    
	public SingleComponentEffectDto getsingleComponentChange(String portfolioId) throws IOException;
}
