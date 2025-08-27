package org.synechron.esg.alternative.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

/**
 * The type Alternative response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeResponse implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Alternatives.
     */
    List<Alternatives> alternatives;
    /**
     * The Historical performance.
     */
    HistoricalPerformanceResponse historicalPerformance;
    /**
     * The Simulated portfolio.
     */
    PortfolioSimulationResponse simulatedPortfolio;
    /**
     * The Status.
     */
    HttpStatus status;
    /**
     * The Status code.
     */
    int statusCode;

}
