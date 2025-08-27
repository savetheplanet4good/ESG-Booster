package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPeerAverageScores {

    private Double totalEsgScore;
    private Integer rankPercentile;
    private Integer outlierScore;
    private Integer rank;
}
