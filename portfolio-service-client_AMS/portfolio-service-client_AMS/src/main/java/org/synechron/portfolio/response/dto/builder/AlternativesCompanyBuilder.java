package org.synechron.portfolio.response.dto.builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.synechron.esg.model.AlternativesCompany;
import org.synechron.portfolio.utils.PortfolioUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativesCompanyBuilder {

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

    public AlternativesCompanyBuilder withISIN(String isin){
        this.isin = isin;
        return this;

    }


    public AlternativesCompanyBuilder withCompanyName(String companyName){
        this.companyName = companyName;
        return this;

    }

    public AlternativesCompanyBuilder withESGCombinedScore(Double esgCombinedScore){
        this.esgCombinedScore = PortfolioUtils.formatDouble(esgCombinedScore);
        return this;

    }

    public AlternativesCompanyBuilder withESGScore(Double esgScore){
        this.esgScore = PortfolioUtils.formatDouble(esgScore);
        return this;

    }

    public AlternativesCompanyBuilder with(Double esgScore){
        this.esgCombinedScore = PortfolioUtils.formatDouble(esgScore);
        return this;

    }

    public AlternativesCompanyBuilder withESGControversiesScore(Double esgControversiesScore){
        this.esgControversiesScore = PortfolioUtils.formatDouble(esgControversiesScore);
        return this;

    }

    public AlternativesCompanyBuilder withEnvScore(Double envScore){
        this.envScore = PortfolioUtils.formatDouble(envScore);
        return this;

    }

    public AlternativesCompanyBuilder withSocialScore(Double socialScore){
        this.socialScore = PortfolioUtils.formatDouble(socialScore);
        return this;

    }

    public AlternativesCompanyBuilder withGovScore(Double govScore){
        this.govScore = PortfolioUtils.formatDouble(govScore);
        return this;

    }

    public AlternativesCompanyBuilder withSustainalyticsTotalEsgScore(Double sustainalyticsTotalEsgScore){
        this.sustainalyticsTotalEsgScore = PortfolioUtils.formatDouble(sustainalyticsTotalEsgScore);
        return this;

    }

    public AlternativesCompanyBuilder withSustainalyticsEnvScore(Double sustainalyticsEnvScore){
        this.sustainalyticsEnvScore = PortfolioUtils.formatDouble(sustainalyticsEnvScore);
        return this;

    }


    public AlternativesCompany build(){
        return new AlternativesCompany(isin,companyName,esgScore,esgCombinedScore,esgControversiesScore,envScore,socialScore,govScore,sustainalyticsTotalEsgScore,sustainalyticsEnvScore,sustainalyticsSocialScore,sustainalyticsGovScore,country,countryName,isPortfolioCompany,sector,isinType,esgMsciScore,fdNr,fdA,fdAA,fdAAA,fdB,fdBB,fdBBB,fdCCC,fundEsgScore);
    }

    public AlternativesCompanyBuilder withSustainalyticsSocialScore(Double sustainalyticsSocialScore){
        this.sustainalyticsSocialScore = PortfolioUtils.formatDouble(sustainalyticsSocialScore);
        return this;

    }

    public AlternativesCompanyBuilder withSustainalyticsGovScore(Double sustainalyticsGovScore){
        this.sustainalyticsGovScore = PortfolioUtils.formatDouble(sustainalyticsGovScore);
        return this;

    }

    public AlternativesCompanyBuilder withCountry(String country){
        this.country = country;
        return this;
    }

    public AlternativesCompanyBuilder withCountryName(String countryName){
        this.countryName = countryName;
        return this;
    }

    public AlternativesCompanyBuilder withIsPortfolioCompany(Boolean isPortfolioCompany){
        this.isPortfolioCompany = isPortfolioCompany;
        return this;
    }

    public AlternativesCompanyBuilder withSector(String sector){
        this.sector = sector;
        return this;
    }

    public AlternativesCompanyBuilder withIsinType(String isinType){
        this.isinType = isinType;
        return this;

    }

    public AlternativesCompanyBuilder withEsgMsciScore(String esgMsciScore){
        this.esgMsciScore = esgMsciScore;
        return this;

    }

    public AlternativesCompanyBuilder withFdNr(Double fdNr){
        this.fdNr = fdNr;
        return this;

    }

    public AlternativesCompanyBuilder withFdA(Double fdA){
        this.fdA = fdA;
        return this;

    }

    public AlternativesCompanyBuilder withFdAA(Double fdAA){
        this.fdAA = fdAA;
        return this;

    }

    public AlternativesCompanyBuilder withFdAAA(Double fdAAA){
        this.fdAAA = fdAAA;
        return this;

    }

    public AlternativesCompanyBuilder withFdB(Double fdB){
        this.fdB = fdB;
        return this;

    }

    public AlternativesCompanyBuilder withFdBB(Double fdBB){
        this.fdBB = fdBB;
        return this;

    }

    public AlternativesCompanyBuilder withFdBBB(Double fdBBB){
        this.fdBBB = fdBBB;
        return this;

    }

    public AlternativesCompanyBuilder withFdCCC(Double fdCCC){
        this.fdCCC = fdCCC;
        return this;

    }

    public AlternativesCompanyBuilder withFundEsgScore(Double fundEsgScore){
        this.fundEsgScore = fundEsgScore;
        return this;

    }
}
