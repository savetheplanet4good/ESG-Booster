package org.synechron.esg.alternative.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * The type Historical performance.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalPerformance {

    /**
     * The Original portfolio.
     */
    Map<String, Double> original_portfolio;
    /**
     * The Recommended portfolio.
     */
    Map<String, Double> recommended_portfolio;
}
