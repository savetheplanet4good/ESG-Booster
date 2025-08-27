package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioListDto {

    String portfolioId;
    String portfolioName;
    int totalCompanies;
    Double esgScore;
    Double totalESGScore;
    Double envScore;
    Double socialScore;
    Double govScore;
    Double contraversialScore;
    String portfolioIsinType;
    String esgMsciScore;
}
