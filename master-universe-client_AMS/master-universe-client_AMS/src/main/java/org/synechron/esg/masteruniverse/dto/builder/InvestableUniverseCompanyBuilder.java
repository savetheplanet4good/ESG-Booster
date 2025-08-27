package org.synechron.esg.masteruniverse.dto.builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.synechron.esg.model.AlternativesCompany;
import org.synechron.esg.masteruniverse.utils.MasterUniverseUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestableUniverseCompanyBuilder {

    private String isin;
    private String companyName;
    private Double esgScore;
    private Double esgCombinedScore;
    private Double esgControversiesScore;
    private Double envScore;
    private Double socialScore;
    private Double govScore;
    private Double sustainalyticsTotalEsgScore;
    private Double sustainalyticsEnvScore;
    private Double sustainalyticsSocialScore;
    private Double sustainalyticsGovScore;
    private String country;
    private String countryName;
    private Boolean isPortfolioCompany;
    private String sector;
    private String isinType;

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

    public InvestableUniverseCompanyBuilder withISIN(String isin){
        this.isin = isin;
        return this;

    }


    public InvestableUniverseCompanyBuilder withCompanyName(String companyName){
        this.companyName = companyName;
        return this;

    }

    public InvestableUniverseCompanyBuilder withESGCombinedScore(Double esgCombinedScore){
        this.esgCombinedScore = MasterUniverseUtils.formatDouble(esgCombinedScore);
        return this;

    }

    public InvestableUniverseCompanyBuilder withESGScore(Double esgScore){
        this.esgScore = MasterUniverseUtils.formatDouble(esgScore);
        return this;

    }

    public InvestableUniverseCompanyBuilder with(Double esgScore){
        this.esgCombinedScore = MasterUniverseUtils.formatDouble(esgScore);
        return this;

    }

    public InvestableUniverseCompanyBuilder withESGControversiesScore(Double esgControversiesScore){
        this.esgControversiesScore = MasterUniverseUtils.formatDouble(esgControversiesScore);
        return this;

    }

    public InvestableUniverseCompanyBuilder withEnvScore(Double envScore){
        this.envScore = MasterUniverseUtils.formatDouble(envScore);
        return this;

    }

    public InvestableUniverseCompanyBuilder withSocialScore(Double socialScore){
        this.socialScore = MasterUniverseUtils.formatDouble(socialScore);
        return this;

    }

    public InvestableUniverseCompanyBuilder withGovScore(Double govScore){
        this.govScore = MasterUniverseUtils.formatDouble(govScore);
        return this;

    }

    public InvestableUniverseCompanyBuilder withSustainalyticsTotalEsgScore(Double sustainalyticsTotalEsgScore){
        this.sustainalyticsTotalEsgScore = MasterUniverseUtils.formatDouble(sustainalyticsTotalEsgScore);
        return this;

    }

    public InvestableUniverseCompanyBuilder withSustainalyticsEnvScore(Double sustainalyticsEnvScore){
        this.sustainalyticsEnvScore = MasterUniverseUtils.formatDouble(sustainalyticsEnvScore);
        return this;

    }


    public AlternativesCompany build(){
        return new AlternativesCompany(isin,companyName,esgScore,esgCombinedScore,esgControversiesScore,envScore,socialScore,govScore,sustainalyticsTotalEsgScore,sustainalyticsEnvScore,sustainalyticsSocialScore,sustainalyticsGovScore,country,countryName,isPortfolioCompany,sector,isinType,esgMsciScore,fdNr,fdA,fdAA,fdAAA,fdB,fdBB,fdBBB,fdCCC,fundEsgScore);
    }

    public InvestableUniverseCompanyBuilder withSustainalyticsSocialScore(Double sustainalyticsSocialScore){
        this.sustainalyticsSocialScore = MasterUniverseUtils.formatDouble(sustainalyticsSocialScore);
        return this;

    }

    public InvestableUniverseCompanyBuilder withSustainalyticsGovScore(Double sustainalyticsGovScore){
        this.sustainalyticsGovScore = MasterUniverseUtils.formatDouble(sustainalyticsGovScore);
        return this;

    }

    public InvestableUniverseCompanyBuilder withCountry(String country){
        this.country = country;
        return this;
    }

    public InvestableUniverseCompanyBuilder withCountryName(String countryName){
        this.countryName = countryName;
        return this;
    }

    public InvestableUniverseCompanyBuilder withIsPortfolioCompany(Boolean isPortfolioCompany){
        this.isPortfolioCompany = isPortfolioCompany;
        return this;
    }

    public InvestableUniverseCompanyBuilder withSector(String sector){
        this.sector = sector;
        return this;
    }

    public InvestableUniverseCompanyBuilder withIsinType(String isinType){
        this.isinType = isinType;
        return this;

    }

    public InvestableUniverseCompanyBuilder withEsgMsciScore(String esgMsciScore){
        this.esgMsciScore = esgMsciScore;
        return this;

    }

    public InvestableUniverseCompanyBuilder withFdNr(Double fdNr){
        this.fdNr = fdNr;
        return this;

    }

    public InvestableUniverseCompanyBuilder withFdA(Double fdA){
        this.fdA = fdA;
        return this;

    }

    public InvestableUniverseCompanyBuilder withFdAA(Double fdAA){
        this.fdAA = fdAA;
        return this;

    }

    public InvestableUniverseCompanyBuilder withFdAAA(Double fdAAA){
        this.fdAAA = fdAAA;
        return this;

    }

    public InvestableUniverseCompanyBuilder withFdB(Double fdB){
        this.fdB = fdB;
        return this;

    }

    public InvestableUniverseCompanyBuilder withFdBB(Double fdBB){
        this.fdBB = fdBB;
        return this;

    }

    public InvestableUniverseCompanyBuilder withFdBBB(Double fdBBB){
        this.fdBBB = fdBBB;
        return this;

    }

    public InvestableUniverseCompanyBuilder withFdCCC(Double fdCCC){
        this.fdCCC = fdCCC;
        return this;

    }

    public InvestableUniverseCompanyBuilder withFundEsgScore(Double fundEsgScore){
        this.fundEsgScore = fundEsgScore;
        return this;

    }

}
