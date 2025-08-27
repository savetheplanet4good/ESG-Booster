package org.synechron.esg.benchmarkservice.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BenchmarkDataLakeResponse {

    private String source;
    private String indexName;
    private Double indexTotalScore;
    private Double envIndexScore;
    private Double socialIndexScore;
    private Double govIndexScore;
    private String esgMsciScore;
    private Double fdNr;
    private Double fdA;
    private Double fdAA;
    private Double fdAAA;
    private Double fdB;
    private Double fdBB;
    private Double fdBBB;
    private Double fdCCC;
}
