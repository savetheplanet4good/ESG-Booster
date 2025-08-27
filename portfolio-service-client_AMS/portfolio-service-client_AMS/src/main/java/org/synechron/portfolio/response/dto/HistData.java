package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistData {

    private String date;
    private Double esgCombinedScore;
    private Double esgScore;
    private Double sentimentScore;
    private Double environmentalScore;
    private Double socialScore;
    private Double governanceScore;
}
