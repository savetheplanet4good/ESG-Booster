package org.synechron.portfolio.response.insights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SectorDataResponseDto {

    private String title;
    private String displayMessage;
    private int portfolioSectorCount;
    private int totalSectorCount;
}
