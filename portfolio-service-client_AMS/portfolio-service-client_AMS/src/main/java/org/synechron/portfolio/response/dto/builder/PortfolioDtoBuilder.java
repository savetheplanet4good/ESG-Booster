package org.synechron.portfolio.response.dto.builder;

import org.synechron.esg.model.*;
import org.synechron.portfolio.response.dto.FactorDto;
import org.synechron.portfolio.response.dto.PortfolioDto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PortfolioDtoBuilder {

    private static DecimalFormat df = new DecimalFormat("##.00");


    private String portfolioId;
    private String portfolioName;
    private String portfolioType;
    private String investableUniverseType;
    private Double esgCombinedScore;
    private Double esgScore;
    private Double envScore;
    private Double socialScore;
    private Double govScore;
    private List<FactorDto> environmentalFactors;
    private List<FactorDto>socialFactors;
    private List<FactorDto>governanceFactors;
    private int companyCount;
    private int outLierCount;
    private Fund fund;
    private Equity equity;
    private String calculationType;
    private String portfolioIsinsType;
    private String esgMsciScore;

    public PortfolioDtoBuilder withPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
        return this;
    }
    public PortfolioDtoBuilder withPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
        return this;
    }

    public PortfolioDtoBuilder withPortfolioType(String portfolioType) {
        this.portfolioType = portfolioType;
        return this;
    }

    public PortfolioDtoBuilder withInvestableUniverseType(String investableUniverseType) {
        this.investableUniverseType = investableUniverseType;
        return this;
    }

    public PortfolioDtoBuilder withCombinedESGScore(Double combinedESGScore){
        this.esgCombinedScore = fomatDouble(combinedESGScore);
        return this;
    }

    public PortfolioDtoBuilder withESGScore(Double esgScore) {
        this.esgScore = fomatDouble( esgScore );
        return this;
    }

    public PortfolioDtoBuilder withEnvScore(Double envScore) {
        this.envScore = fomatDouble(envScore);
        return this;
    }
    public PortfolioDtoBuilder withSocialScore(Double socialScore) {
        this.socialScore = fomatDouble(socialScore);
        return this;
    }

    public PortfolioDtoBuilder withGovScore(Double govScore) {
        this.govScore = fomatDouble(govScore);
        return this;
    }

    public PortfolioDtoBuilder withEnvironmentalFactors(List<EnvironmentalFactor> environmentalFactors) {
        this.environmentalFactors = convertToEnvFactorlist(environmentalFactors);
        return this;
    }

    public PortfolioDtoBuilder withSocialFactors(List<SocialFactor> socialFactors) {
        this.socialFactors = convertToSocialFactorlist(socialFactors);
        return this;
    }

    public PortfolioDtoBuilder withGovernanceFactors(List<GovernanceFactor> govFactors) {
        this.governanceFactors = convertToGovFactorlist(govFactors);
        return this;
    }

    private Double fomatDouble(Double inputValue){
        Double outputValue = Double.parseDouble(df.format(0));
        if (inputValue != null)
            outputValue = Double.parseDouble(df.format(inputValue));
        return outputValue;

    }

    public PortfolioDtoBuilder withComponyCount(int companyCount){
        this.companyCount = companyCount;
        return this;
    }
    
    public PortfolioDtoBuilder withOutLierCount(int outLierCount){
        this.outLierCount = outLierCount;
        return this;
    }

    public PortfolioDtoBuilder withFund(Fund fund){
        this.fund = fund;
        return this;
    }

    public PortfolioDtoBuilder withEquity(Equity equity){
        this.equity = equity;
        return  this;
    }

    public PortfolioDtoBuilder withCalculationType(String calculationType){
        this.calculationType = calculationType;
        return this;
    }

    public PortfolioDto build() {
        return new PortfolioDto(portfolioId,portfolioName,portfolioType,investableUniverseType,
                esgCombinedScore,esgScore,envScore,socialScore,govScore,environmentalFactors,
                socialFactors,governanceFactors,companyCount,outLierCount,fund,equity,calculationType,portfolioIsinsType, esgMsciScore);
     }


    private List<FactorDto> convertToSocialFactorlist(List<SocialFactor> socialFactors){
        List<FactorDto> factorList = new ArrayList<>();
        for (SocialFactor socialFactor : socialFactors)
            factorList.add(new FactorDto(socialFactor.getName(),socialFactor.getScore()));

        return  factorList;
    }

    private List<FactorDto> convertToEnvFactorlist(List<EnvironmentalFactor> environmentalFactors){
        List<FactorDto> factorList = new ArrayList<>();
        for (EnvironmentalFactor envFactor : environmentalFactors)
            factorList.add(new FactorDto(envFactor.getName(),envFactor.getScore()));

        return  factorList;
    }

    private List<FactorDto> convertToGovFactorlist (List<GovernanceFactor> govFactors){
        List<FactorDto> factorList = new ArrayList<>();
        for (GovernanceFactor governanceFactor : govFactors)
            factorList.add(new FactorDto(governanceFactor.getName(),governanceFactor.getScore()));

        return  factorList;
    }

    public PortfolioDtoBuilder withPortfolioIsinsType(String portfolioIsinsType) {
        this.portfolioIsinsType = portfolioIsinsType;
        return this;
    }

    public PortfolioDtoBuilder withEsgMsciScore(String esgMsciScore) {
        this.esgMsciScore = esgMsciScore;
        return this;
    }
}
