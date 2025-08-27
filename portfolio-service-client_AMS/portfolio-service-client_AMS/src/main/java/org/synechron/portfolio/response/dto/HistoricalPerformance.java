package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalPerformance {

    Map<String, Double> original_portfolio;
    Map<String, Double> recommended_portfolio;
}
