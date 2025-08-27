package org.synechron.portfolio.response.insights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PortfolioInsightsResponseDto {

    private ESGScorePercentageChangeDto esgPercentageChangeInsights;
    private SingleComponentEffectDto singleComponentChangeInsight;
    private SectorDataResponseDto sectorInsights;
    private NewsSentimentCountResponseDto newsSentimentInsights;
    private EsgInfluenceCountResponseDto esgInfluenceInsights;
    private OutlierRangeResponseDto outlierRangeInsights;
}
