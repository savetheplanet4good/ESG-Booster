package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPeerAverageUIResponseDto {

    private String industryName;
    private Double ds1SectorAverage;
    private Double ds2SectorAverage;
    private Double ds1BestEsg;
    private Double ds2BestEsg;
    private List<CompanyPeerAverageDto> companies;
}
