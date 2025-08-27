package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CumulativeFundsBenchmark {

    private String indexName;
    private Double cumulativeESGScore;
}
