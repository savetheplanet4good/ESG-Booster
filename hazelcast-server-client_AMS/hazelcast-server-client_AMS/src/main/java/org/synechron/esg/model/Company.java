package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Company implements Serializable{

    private static final long serialVersionUID = 1L;
	private String isin;
    private String name;
    private Double wt;
    private long amountInvested;
    private Double esgScore;
    private Double environmentalScore;
    private Double socialScore;
    private Double governenceScore;
    private Double holdingValue;
    private Double controversyScore;
    private Double esgCombinedScore;
    private Double totalESGCombinedScore;
    private Double totalESGScore;
    private Double totalEnvironmentalScore;
    private Double totalSocialScore;
    private Double totalGovernanceScore;
    private Double influenceESGCombinedScore;
    private Double influenceESGSCore;
    private Double influenceEnvironmentalScore;
    private Double influenceSocialScore;
    private Double influenceGovernanceScore;

    private List<EnvironmentalFactor> environmentalFactors;
    private List<SocialFactor> socialFactors;
    private List<GovernanceFactor> governanceFactors;
    private Double sustainlyticsEsgScore;
    private Double sustainlyticsEnvironmentalScore;
    private Double sustainlyticsSocialScore;
    private Double sustainlyticsGovernenceScore;
    private Double sustainalyticsTotalESGScore;
    private Double sustainalyticsTotalEnvironmentalScore;
    private Double sustainalyticsTotalSocialScore;
    private Double sustainalyticsTotalGovernanceScore;
    private Double influenceESGScoreForSustainalytics;
    private Double influenceEnvironmentalScoreForSustainalytics;
    private Double influenceSocialScoreForSustainalytics;
    private Double influenceGovernanceScoreForSustainalytics;
    private Double sustainlyticsNormalisedScore;
    private Double outlierScore;
    private Double envOutlierScore;
    private Double socialOutlierScore;
    private Double govOutlierScore;
    private Double refinitivNormalisedScore;
    private Boolean isOutlier=Boolean.FALSE;
    private String isinType;
    private String sector;
    private Double newsSentimentScore;

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
    private Double totalFundEsgScore;

    public Company(String isin, String name, double wt, long amountInvested) {
        this.isin = isin;
        this.name = name;
        this.wt = wt;
        this.amountInvested = amountInvested;
    }
}