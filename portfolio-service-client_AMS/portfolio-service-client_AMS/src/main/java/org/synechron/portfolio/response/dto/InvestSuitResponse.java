package org.synechron.portfolio.response.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@JsonFormat
public class InvestSuitResponse {

    @JsonProperty("recommended_trades")
    private Map<String, Double> recommended_trades;

    @JsonProperty("esg_differences")
    private ESGDifferences esg_differences;

    @JsonProperty("allocation_differences")
    private AllocationDifferences allocation_differences;

    @JsonProperty("historical_performance")
    private HistoricalPerformance historical_performance;

    @JsonProperty("tracking_error")
    private Double tracking_error;
}
