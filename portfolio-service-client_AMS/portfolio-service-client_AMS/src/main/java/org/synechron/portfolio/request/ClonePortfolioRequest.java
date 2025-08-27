package org.synechron.portfolio.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClonePortfolioRequest {
    private String portfolioId;
    private String portfolioName;
}
