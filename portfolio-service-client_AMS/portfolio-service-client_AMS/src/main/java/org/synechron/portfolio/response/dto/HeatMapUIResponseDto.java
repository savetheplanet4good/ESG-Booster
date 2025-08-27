package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
