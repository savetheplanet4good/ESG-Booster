package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Company esg score.
 */
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

    private List<EnvironmentalFactor> environmentalFactors = new ArrayList<EnvironmentalFactor>();
    ;
    private List<SocialFactor> socialFactors = new ArrayList<SocialFactor>();
    private List<GovernanceFactor> governanceFactors = new ArrayList<GovernanceFactor>();


    @Override
    public String toString() {
        return "CompanyESGScore [ticker=" + ticker + ", isin=" + isin + ", isinType=" + isinType + ", instrumentName=" + instrumentName
                + ", refinitiveIndustryName=" + refinitiveIndustryName + ", sectors=" + sectors + ", esgScore="
                + esgScore + ", esgCombinedScore=" + esgCombinedScore + ", esgControversiesScore="
                + esgControversiesScore + ", environmentPillarScore=" + environmentPillarScore + ", socialPillarScore="
                + socialPillarScore + ", governancePillarScore=" + governancePillarScore
                + ", sustainalyticsTotalEsgScore=" + sustainalyticsTotalEsgScore + ", sustainalyticsEnvironmentScore="
                + sustainalyticsEnvironmentScore + ", sustainalyticsSocialScore=" + sustainalyticsSocialScore
                + ", sustainalyticsGovernanceScore=" + sustainalyticsGovernanceScore
                + ", sustainalyticsNormalisedScore=" + sustainalyticsNormalisedScore + ", outlier=" + outlier
				+ ", country=" + country + ", countryName=" + countryName + ", currencyCode=" + currencyCode + ", currencySymbol=" + currencySymbol
				+ ", refinitivNormalizedScore=" + refinitivNormalizedScore + ", environmentalFactors="
				+ environmentalFactors + ", socialFactors=" + socialFactors + ", governanceFactors=" + governanceFactors
				+ "]";
    }

}
