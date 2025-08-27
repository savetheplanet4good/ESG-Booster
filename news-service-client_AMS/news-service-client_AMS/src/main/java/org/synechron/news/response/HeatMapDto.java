package org.synechron.news.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * Class Name : HeatMapDto
 * Purpose : News heat map responseDto object
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeatMapDto {
    private String parent;
    private String value;
    private Double sentimentScore;
    private Integer noOfArticles;
}
