package org.synechron.esg.masteruniverse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyESGScore {

    private String ticker;
    private String isin;
    private String isinType;
    private String instrumentName;
    private String refinitiveIndustryName;
    private String sectors;
    private Double esgScore;
    private Double esgCombinedScore;
    private Double esgControversiesScore;
    private Double environmentPillarScore;
    private Double socialPillarScore;
    private Double governancePillarScore;
    private Double sustainalyticsTotalEsgScore;
    private Double sustainalyticsEnvironmentScore;
    private Double sustainalyticsSocialScore;
    private Double sustainalyticsGovernanceScore;
    private Double sustainalyticsNormalisedScore;
    private Double outlier;

    private String country;
    private String countryName;
    private String currencyCode;
    private String currencySymbol;
    private Double refinitivNormalizedScore;

    //Fund related changes
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
