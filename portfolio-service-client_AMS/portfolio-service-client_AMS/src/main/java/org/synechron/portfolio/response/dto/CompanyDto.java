package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.synechron.esg.model.NewsDto;
import java.util.List;

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
    private List<FactorDto> environmentalFactors;
    private List<FactorDto> socialFactors;
    private List<FactorDto> governanceFactors;
    private NewsDto news;
    private double outlierScore;
    private double envOutlierScore;
    private double socialOutlierScore;
    private double govOutlierScore;
    private double refinitivNormalisedScore;
    private double sustainalyticsNormalisedScore;
    private double refinitivESGCombinedScore;
    private double sustainalyticsESGCombinedScore;
    private Double sustainalyticsEnvironmentScore;
    private Double sustainalyticsSocialScore;
    private Double sustainalyticsGovernanceScore;
    private Boolean isOutLier;
    private String isinType;
    private Double newsSentimentScore;
    private String sector;
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
}
