package org.synechron.portfolio.response.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalScores {

    @JsonProperty("Date")
    private String Date;

    @JsonProperty("ESG Score")
    private Double ESG_Score;

    private Double sentiment_score_avg;

    @JsonProperty("Environment Pillar Score")
    private Double environmentalScore;

    @JsonProperty("Social Pillar Score")
    private Double socialScore;

    @JsonProperty("Governance Pillar Score")
    private Double governanceScore;
}
