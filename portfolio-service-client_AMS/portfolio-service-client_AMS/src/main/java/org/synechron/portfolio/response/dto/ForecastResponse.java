package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForecastResponse {

    private List<Forecast> predictions_without_sentiment;
    private List<Forecast> predictions_with_sentiment;
    private List<Forecast> actual;
}
