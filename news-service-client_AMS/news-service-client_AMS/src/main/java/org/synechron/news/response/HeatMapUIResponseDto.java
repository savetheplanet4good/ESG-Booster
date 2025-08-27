package org.synechron.news.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * Class Name : HeatMapUIResponseDto
 * Purpose : News heat map ui responseDto object
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeatMapUIResponseDto {
    private String id;
    private String name;
    private String parent;
    private Double sentimentScore;
    private Integer value;
}
