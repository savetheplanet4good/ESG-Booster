package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeatMapResponseDto {

    private Double averageSentimentScore;
    private List<HeatMapUIResponseDto> heatMapData;
}
