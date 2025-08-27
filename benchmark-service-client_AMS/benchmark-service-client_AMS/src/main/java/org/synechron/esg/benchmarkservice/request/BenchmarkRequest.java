package org.synechron.esg.benchmarkservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * Class Name : BenchmarkRequest
 * Purpose : Benchmark request object
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BenchmarkRequest {
    private List<String> dataSourcesList;
    private List<String> indexList;
}
