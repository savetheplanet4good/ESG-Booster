package org.synechron.portfolio.response.insights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OutlierRangeResponseDto {

    private String title;
    private String displayMessage;
    private int minValue;
    private int maxValue;
}
