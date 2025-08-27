package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(collection = "portfolio")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Portfolio implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @Field(name="portfolioId")
    private String portfolioId;

    @Field(name="portfolioName")
    private String portfolioName;

    @Field(name="portfolioType")
    private String portfolioType;

    @Field(name="investableUniverseType")
    private String investableUniverseType;

    @Field(name="portfolioUser")
    private String portfolioUser;

    @Field(name="companies")
    private List<Company> companies;

    @Field(name="environmentalFactors")
    private List<EnvironmentalFactor> environmentalFactors;

    @Field(name="socialFactors")
    private List<SocialFactor> socialFactors;

    @Field(name="governanceFactors")
    private List<GovernanceFactor> governanceFactors;

    @Field(name="esgScore")
    private Double esgScore;

    @Field(name="envScore")
    private Double envScore;

    @Field(name="socialScore")
    private Double socialScore;

    @Field(name="govScore")
    private Double govScore;

    @Field(name="esgCombinedScore")
    private Double esgCombinedScore;

    @Field(name="contraversyScore")
    private Double contraversyScore;

    @Field(name="investableUniverseFilter")
    private InvestableUniverseFilter investableUniverseFilter;
    
    @Field(name="sustEnvScore")
    private Double sustainalyticsEnvScore;

    @Field(name="sustSocialScore")
    private Double sustainalyticsSocialScore;

    @Field(name="sustGovScore")
    private Double sustainalyticsGovScore;

    @Field(name="sustEsgCombinedScore")
    private Double sustainalyticsCombinedScore;

    @Field(name="sector")
    private Sector sector;

    @Field(name="regions")
    private List<String> regions;

    @Field(name="currency")
    private List<String> currency;
    
    @Field(name="created_date")
    private Date createdDate=new Date();

    @Field(name="currencyAllocation")
    private CurrencyAllocation currencyAllocation;

    @Field(name="countryAllocation")
    private CountryAllocation countryAllocation;

    @Field(name="fund")
    private Fund fund;

    @Field(name="equity")
    private Equity equity;

    @Field(name="calculationType")
    private String calculationType;
    
    @Field(name="portfolioIsinsType")
    private String portfolioIsinsType;

    @Field(name="fundEsgScore")
    private Double fundEsgScore;

    @Field(name="esgMsciScore")
    private String esgMsciScore;

    @Field(name="fdNr")
    private Double fdNr;

    @Field(name="fdA")
    private Double fdA;

    @Field(name="fdAA")
    private Double fdAA;

    @Field(name="fdAAA")
    private Double fdAAA;

    @Field(name="fdB")
    private Double fdB;

    @Field(name="fdBB")
    private Double fdBB;

    @Field(name="fdBBB")
    private Double fdBBB;

    @Field(name="fdCCC")
    private Double fdCCC;

    public Portfolio(List<Company> companies, List<EnvironmentalFactor> environmentalFactors, List<SocialFactor> socialFactors, List<GovernanceFactor> governanceFactors, Double esgScore, Double envScore, Double socialScore, Double govScore, Double esgCombinedScore, Double contraversyScore, Double sustainalyticsEnvScore, Double sustainalyticsSocialScore, Double sustainalyticsGovScore, Double sustainalyticsCombinedScore) {
        this.companies = companies;
        this.environmentalFactors = environmentalFactors;
        this.socialFactors = socialFactors;
        this.governanceFactors = governanceFactors;
        this.esgScore = esgScore;
        this.envScore = envScore;
        this.socialScore = socialScore;
        this.govScore = govScore;
        this.esgCombinedScore = esgCombinedScore;
        this.contraversyScore = contraversyScore;
        this.sustainalyticsEnvScore = sustainalyticsEnvScore;
        this.sustainalyticsSocialScore = sustainalyticsSocialScore;
        this.sustainalyticsGovScore = sustainalyticsGovScore;
        this.sustainalyticsCombinedScore = sustainalyticsCombinedScore;
    }
}