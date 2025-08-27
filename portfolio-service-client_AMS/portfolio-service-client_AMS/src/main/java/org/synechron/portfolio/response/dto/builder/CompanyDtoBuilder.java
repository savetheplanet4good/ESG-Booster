package org.synechron.portfolio.response.dto.builder;

import org.synechron.esg.model.EnvironmentalFactor;
import org.synechron.esg.model.GovernanceFactor;
import org.synechron.esg.model.NewsDto;
import org.synechron.esg.model.SocialFactor;
import org.synechron.portfolio.response.dto.CompanyDto;
import org.synechron.portfolio.response.dto.FactorDto;
import org.synechron.portfolio.utils.PortfolioUtils;
import java.util.ArrayList;
import java.util.List;

public class CompanyDtoBuilder {

    private String isin;
    private String name;
    private double wt;
    private double esgScore;
    private double environmentalScore;
    private double socialScore;
    private double governenceScore;
    private double holdingValue;
    private double esgCombinedScore;
    private double controversyScore;
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
    private double outlierScore;
    private double envOutlierScore;
    private double socialOutlierScore;
    private double govOutlierScore;
    private double refinitivNormalisedScore;
    private double sustainalyticsNormalisedScore;
    private double refinitivESGCombinedScore;
    private Double sustainalyticsESGCombinedScore;
    private Double sustainalyticsEnvironmentScore;
    private Double sustainalyticsSocialScore;
    private Double sustainalyticsGovernanceScore;
    private Boolean isOutLier;
    private NewsDto news;
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

    public CompanyDtoBuilder withISIN(String isin) {
        this.isin = isin;
        return this;
    }

    public CompanyDtoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CompanyDtoBuilder withWt(double wt) {
        this.wt = wt;
        return this;
    }

    public CompanyDtoBuilder withESGScore(double esgScore) {
        this.esgScore = PortfolioUtils.formatDouble(esgScore);
        return this;
    }

    public CompanyDtoBuilder withEnvScore(double environmentalScore) {
        this.environmentalScore = PortfolioUtils.formatDouble(environmentalScore);
        return this;
    }

    public CompanyDtoBuilder withSocialScore(double socialScore) {
        this.socialScore = PortfolioUtils.formatDouble(socialScore);
        return this;
    }

    public CompanyDtoBuilder withGovScore(double governenceScore) {
        this.governenceScore = PortfolioUtils.formatDouble(governenceScore);
        return this;
    }

    public CompanyDtoBuilder withControversyScore(double controversyScore) {
        this.controversyScore = PortfolioUtils.formatDouble(controversyScore);
        return this;
    }

    public CompanyDtoBuilder withESGCombinedScore(double esgCombinedScore) {
        this.esgCombinedScore = PortfolioUtils.formatDouble(esgCombinedScore);
        return this;
    }

    public CompanyDtoBuilder withHoldingValue(double holdingValue) {
        this.holdingValue = PortfolioUtils.formatDouble(holdingValue);
        return this;
    }


    public CompanyDtoBuilder withTotalESGCombinedScore(double totalESGCombinedScore) {
        this.totalESGCombinedScore = PortfolioUtils.formatDouble(totalESGCombinedScore);
        return this;
    }

    public CompanyDtoBuilder withTotalESGScore(double totalESGScore) {
        this.totalESGScore = PortfolioUtils.formatDouble(totalESGScore);
        return this;
    }

    public CompanyDtoBuilder withTotalEnvironmentalScore(double totalEnvironmentalScore) {
        this.totalEnvironmentalScore = PortfolioUtils.formatDouble(totalEnvironmentalScore);
        return this;
    }

    public CompanyDtoBuilder withTotalSocialScore(double totalSocialScore) {
        this.totalSocialScore = PortfolioUtils.formatDouble(totalSocialScore);
        return this;
    }

    public CompanyDtoBuilder withTotalGovernanceScore(double totalGovernanceScore) {
        this.totalGovernanceScore = PortfolioUtils.formatDouble(totalGovernanceScore);
        return this;
    }

    public CompanyDtoBuilder withInfluenceESGCombinedScore(double influenceESGCombinedScore) {
        this.influenceESGCombinedScore = PortfolioUtils.formatDouble(influenceESGCombinedScore);
        return this;
    }

    public CompanyDtoBuilder withInfluenceESGSCore(double influenceESGSCore) {
        this.influenceESGSCore = PortfolioUtils.formatDouble(influenceESGSCore);
        return this;
    }

    public CompanyDtoBuilder withInfluenceEnvironmentalScore(double influenceEnvironmentalScore) {
        this.influenceEnvironmentalScore = PortfolioUtils.formatDouble(influenceEnvironmentalScore);
        return this;
    }

    public CompanyDtoBuilder withInfluenceSocialScore(double influenceSocialScore) {
        this.influenceSocialScore = PortfolioUtils.formatDouble(influenceSocialScore);
        return this;
    }

    public CompanyDtoBuilder withInfluenceGovernanceScore(double influenceGovernanceScore) {
        this.influenceGovernanceScore = PortfolioUtils.formatDouble(influenceGovernanceScore);
        return this;
    }

    public CompanyDtoBuilder withEnvironmentalFactors(List<EnvironmentalFactor> environmentalFactors) {
        this.environmentalFactors = convertToEnvFactorlist(environmentalFactors);
        return this;
    }

    public CompanyDtoBuilder withSocialFactors(List<SocialFactor> socialFactors) {
        this.socialFactors = convertToSocialFactorlist(socialFactors);
        return this;
    }


    public CompanyDtoBuilder withGovernanceFactors(List<GovernanceFactor> govFactors) {
        this.governanceFactors = convertToGovFactorlist(govFactors);
        return this;
    }

    public CompanyDtoBuilder withNews(NewsDto news){
        this.news = news;
        return this;
    }

    private List<FactorDto> convertToSocialFactorlist(List<SocialFactor> socialFactors) {
        List<FactorDto> factorList = new ArrayList<>();
        if(socialFactors==null)
        {
            return new ArrayList<>();
        }
        for (SocialFactor socialFactor : socialFactors)
            factorList.add(new FactorDto(socialFactor.getName(), PortfolioUtils.formatDouble(socialFactor.getScore())));

        return factorList;
    }

    private List<FactorDto> convertToEnvFactorlist(List<EnvironmentalFactor> environmentalFactors) {
        List<FactorDto> factorList = new ArrayList<>();
        if(environmentalFactors==null)
        {
            return new ArrayList<>();
        }
        for (EnvironmentalFactor envFactor : environmentalFactors)
            factorList.add(new FactorDto(envFactor.getName(), PortfolioUtils.formatDouble(envFactor.getScore())));

        return factorList;
    }

    private List<FactorDto> convertToGovFactorlist(List<GovernanceFactor> govFactors) {
        List<FactorDto> factorList = new ArrayList<>();
        if(govFactors==null)
        {
            return new ArrayList<>();
        }
        for (GovernanceFactor governanceFactor : govFactors)
            factorList.add(new FactorDto(governanceFactor.getName(), PortfolioUtils.formatDouble(governanceFactor.getScore())));

        return factorList;
    }

    public CompanyDtoBuilder withOutLierScore(double outlierScore) {
        this.outlierScore = PortfolioUtils.formatDouble(outlierScore);
        return this;
    }


    public CompanyDtoBuilder withSocialOutLierScore(double socialOutlierScore) {
        this.socialOutlierScore = PortfolioUtils.formatDouble(socialOutlierScore);
        return this;
    }

    public CompanyDtoBuilder withEnvOutLierScore(double envOutlierScore) {
        this.envOutlierScore = PortfolioUtils.formatDouble(envOutlierScore);
        return this;
    }

    public CompanyDtoBuilder withGovOutLierScore(double govOutlierScore) {
        this.govOutlierScore = PortfolioUtils.formatDouble(govOutlierScore);
        return this;
    }

    public CompanyDtoBuilder withRefinitivNormalisedScore(double refinitivNormalisedScore) {
        this.refinitivNormalisedScore = refinitivNormalisedScore;
        return this;
    }

    public CompanyDtoBuilder withSustainalyticsNormalisedScore(double sustainalyticsNormalisedScore) {
        this.sustainalyticsNormalisedScore = sustainalyticsNormalisedScore;
        return this;
    }
    
    public CompanyDtoBuilder withRefinitivESGCombinedScore(double refinitivESGCombinedScore) {
        this.refinitivESGCombinedScore = refinitivESGCombinedScore;
        return this;
    }
    
    public CompanyDtoBuilder withSustainalyticsESGCombinedScore(double sustainalyticsESGCombinedScore) {
        this.sustainalyticsESGCombinedScore = sustainalyticsESGCombinedScore;
        return this;
    }


    public CompanyDtoBuilder withSustainalyticsEnvScore(double sustainalyticsEnvironmentScore) {
        this.sustainalyticsEnvironmentScore = sustainalyticsEnvironmentScore;
        return this;
    }

    public CompanyDtoBuilder withSustainalyticsGovScore(double sustainalyticsGovernanceScore) {
        this.sustainalyticsGovernanceScore = sustainalyticsGovernanceScore;
        return this;
    }

    public CompanyDtoBuilder withSustainalyticsSocialScore(double sustainalyticsSocialScore) {
        this.sustainalyticsSocialScore = sustainalyticsSocialScore;
        return this;
    }

    public CompanyDtoBuilder withIsOutLier(Boolean isOutLier) {
        this.isOutLier = isOutLier;
        return this;
    }

    public CompanyDtoBuilder withIsinType(String isinType) {
        this.isinType = isinType;
        return this;
    }

    public CompanyDtoBuilder withNewsSentimentScore(Double newsSentimentScore) {
        this.newsSentimentScore = newsSentimentScore;
        return this;
    }

    public CompanyDtoBuilder withSector(String sector) {
        this.sector = sector;
        return this;
    }

    public CompanyDtoBuilder withEsgMsciScore(String esgMsciScore){
        this.esgMsciScore = esgMsciScore;
        return this;

    }

    public CompanyDtoBuilder withFdNr(Double fdNr){
        this.fdNr = fdNr;
        return this;

    }

    public CompanyDtoBuilder withFdA(Double fdA){
        this.fdA = fdA;
        return this;

    }

    public CompanyDtoBuilder withFdAA(Double fdAA){
        this.fdAA = fdAA;
        return this;

    }

    public CompanyDtoBuilder withFdAAA(Double fdAAA){
        this.fdAAA = fdAAA;
        return this;

    }

    public CompanyDtoBuilder withFdB(Double fdB){
        this.fdB = fdB;
        return this;

    }

    public CompanyDtoBuilder withFdBB(Double fdBB){
        this.fdBB = fdBB;
        return this;

    }

    public CompanyDtoBuilder withFdBBB(Double fdBBB){
        this.fdBBB = fdBBB;
        return this;

    }

    public CompanyDtoBuilder withFdCCC(Double fdCCC){
        this.fdCCC = fdCCC;
        return this;

    }

    public CompanyDtoBuilder withFundEsgScore(Double fundEsgScore){
        this.fundEsgScore = fundEsgScore;
        return this;

    }

    public CompanyDtoBuilder withTotalFundEsgScore(Double totalFundEsgScore){
        this.totalFundEsgScore = totalFundEsgScore;
        return this;

    }

    public CompanyDto build() {

    	return new CompanyDto(isin, name, wt, esgScore, environmentalScore, socialScore, governenceScore, holdingValue, controversyScore,
                esgCombinedScore, totalESGCombinedScore, totalESGScore, totalEnvironmentalScore, totalSocialScore, totalGovernanceScore,
                influenceESGCombinedScore, influenceESGSCore, influenceEnvironmentalScore, influenceSocialScore, influenceGovernanceScore,
                environmentalFactors, socialFactors, governanceFactors,news,outlierScore,envOutlierScore,socialOutlierScore,govOutlierScore,
                refinitivNormalisedScore,sustainalyticsNormalisedScore,
                refinitivESGCombinedScore,sustainalyticsESGCombinedScore,sustainalyticsEnvironmentScore,sustainalyticsSocialScore,
                sustainalyticsGovernanceScore,isOutLier,isinType, newsSentimentScore, sector, esgMsciScore, fdNr, fdA, fdAA, fdAAA, fdB, fdBB, fdBBB, fdCCC, fundEsgScore, totalFundEsgScore);
    }

}
