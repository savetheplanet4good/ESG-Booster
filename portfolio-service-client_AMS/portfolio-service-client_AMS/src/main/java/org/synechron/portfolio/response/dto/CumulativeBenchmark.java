package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CumulativeBenchmark {

    private String indexName;
    private Double cumulativeESGScore;
    private Double cumulativeEnvScore;
    private Double cumulativeSocialScore;
    private Double cumulativeGovScore;
}
