package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Alternatives> alternatives;
    private HistoricalPerformanceResponse historicalPerformance;
    private PortfolioSimulationResponse simulatedPortfolio;
    private HttpStatus status;
    private int statusCode;
}
