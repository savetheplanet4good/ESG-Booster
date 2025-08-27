package org.synechron.news.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * Class Name : HeatMapResponseDto
 * Purpose : News heat map responseDto object
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeatMapResponseDto {
    private Double averageSentimentScore;
    private List<HeatMapUIResponseDto> heatMapData;
}
