package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPeerAverageDto {

    private String isin;
    private String companyName;
    private String sector;
    private String industryName;
    private Boolean isPortfolioCompany;
    private Boolean isOutLier;
    private CompanyPeerAverageScores ds1Scores;
    private CompanyPeerAverageScores ds2Scores;
}
