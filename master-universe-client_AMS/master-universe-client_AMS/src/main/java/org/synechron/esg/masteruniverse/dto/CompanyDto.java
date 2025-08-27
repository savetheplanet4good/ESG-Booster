package org.synechron.esg.masteruniverse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private String isin;
    private String name;
    private double wt;
    private double esgScore;
    private double environmentalScore;
    private double socialScore;
    private double governenceScore;
    private double holdingValue;
    private double controversyScore;
    private double esgCombinedScore;
    private double totalESGCombinedScore;
    private double totalESGScore;
    private double totalEnvironmentalScore;
    private double totalSocialScore;
    private double totalGovernanceScore;
    private double influenceESGCombinedScore;
    private double influenceESGSCore;
    private double influenceEnvironmentalScore;
    private double influenceSocialScore;
    private double influenceGovernanceScore;

    private double outlierScore;
    private double refinitivNormalisedScore;
    private double sustainalyticsNormalisedScore;
    private double refinitivESGCombinedScore;
    private double sustainalyticsESGCombinedScore;
    private Boolean isOutLier;

}
