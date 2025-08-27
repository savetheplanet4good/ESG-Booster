package org.synechron.esg.alternative.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * The type Historical performance response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalPerformanceResponse implements Serializable {
    /**
     * The Origional portfolio.
     */
    List<HistoricalData> origionalPortfolio;
    /**
     * The Recommended portfolio.
     */
    List<HistoricalData> recommended_portfolio;

}
