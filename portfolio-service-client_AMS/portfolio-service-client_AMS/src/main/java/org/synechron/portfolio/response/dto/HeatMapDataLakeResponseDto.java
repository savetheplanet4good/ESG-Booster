package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeatMapDataLakeResponseDto {

    private String parent;
    private String value;
    private Double sentimentScore;
    private Integer noOfArticles;
}
