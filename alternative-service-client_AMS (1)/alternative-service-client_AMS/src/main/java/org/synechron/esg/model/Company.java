package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * The type Company.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Company implements Serializable{
    /**
     *
	 */
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

    /**
     * Instantiates a new Company.
     *
     * @param isin           the isin
     * @param name           the name
     * @param wt             the wt
     * @param amountInvested the amount invested
     */
    public Company(String isin, String name, double wt, long amountInvested) {
        this.isin = isin;
        this.name = name;
        this.wt = wt;
        this.amountInvested = amountInvested;
        this.holdingValue = wt;
    }

    /**
     * Instantiates a new Company.
     *
     * @param isin         the isin
     * @param name         the name
     * @param holdingValue the holding value
     */
    public Company(String isin, String name, Double holdingValue) {
        this.isin = isin;
        this.name = name;
        this.holdingValue = holdingValue;
        this.wt=holdingValue;
    }
}