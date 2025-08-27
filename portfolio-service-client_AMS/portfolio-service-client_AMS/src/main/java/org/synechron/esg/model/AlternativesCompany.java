package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativesCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    private String isin;
    private String companyName;
    private Double esgScore;
    private Double esgCombinedScore;
    private Double esgControversiesScore;
    private Double environmentalScore;
    private Double socialScore;
    private Double governenceScore;
    private Double sustainalyticsTotalEsgScore;
    private Double sustainalyticsEnvScore;
    private Double sustainalyticsSocialScore;
    private Double sustainalyticsGovScore;
    private String country;
    private String countryName;
    private Boolean isPortfolioCompany;
    private String sector;
    private String isinType;
    private String esgMsciScore;
    private Double fdNr;
    private Double fdA;
    private Double fdAA;
    private Double fdAAA;
    private Double fdB;
    private Double fdBB;
    private Double fdBBB;
    private Double fdCCC;
    private Double fundEsgScore;
}
