package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.synechron.esg.model.Benchmark;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundsBenchmarkResponseDto {

    String portfolioId;
    String portfolioName;
    Double totalESG;
    private String esgMsciScore;
    private Double fdNr;
    private Double fdA;
    private Double fdAA;
    private Double fdAAA;
    private Double fdB;
    private Double fdBB;
    private Double fdBBB;
    private Double fdCCC;
    List<Benchmark> benchmarkdata;
    List<CumulativeFundsBenchmark> cumulativeBenchmarks;
}
