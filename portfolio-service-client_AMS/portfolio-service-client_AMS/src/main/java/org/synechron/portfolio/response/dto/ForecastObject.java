package org.synechron.portfolio.response.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class ForecastObject {

    private List<Forecast> predictions_without_sentiment;
    private List<Forecast> predictions_with_sentiment;
    private List<Forecast> actual;
}
