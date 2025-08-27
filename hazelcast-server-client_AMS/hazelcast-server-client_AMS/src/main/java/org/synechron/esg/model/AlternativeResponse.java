package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    List<Alternatives> alternatives;
    HistoricalPerformanceResponse historicalPerformance;
    PortfolioSimulationResponse simulatedPortfolio;
}
