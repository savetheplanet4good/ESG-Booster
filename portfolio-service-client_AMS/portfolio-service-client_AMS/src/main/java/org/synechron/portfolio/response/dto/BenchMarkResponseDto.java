package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.synechron.esg.model.Benchmark;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenchMarkResponseDto {

    String portfolioId;
    String portfolioName;
    Double totalESG;
    Double environmentalScore;
    Double governanceScore;
    Double socialScore;
    List<Benchmark> benchmarkdata;
    List<CumulativeBenchmark> cumulativeBenchmarks;
}
