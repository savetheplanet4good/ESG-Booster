package org.synechron.news.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * Class Name : HeatMapDataLakeResponseDto
 * Purpose : News heat map datalake responseDto object
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeatMapDataLakeResponseDto {
    private String parent;
    private String value;
    private Double sentimentScore;
    private Integer noOfArticles;
}
