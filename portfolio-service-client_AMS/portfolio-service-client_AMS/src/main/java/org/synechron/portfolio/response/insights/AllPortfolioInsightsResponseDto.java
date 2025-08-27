package org.synechron.portfolio.response.insights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AllPortfolioInsightsResponseDto {

    private String portfolioId;
    private PortfolioInsightsResponseDto insights;
}
