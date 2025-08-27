package org.synechron.esg.alternative.response.buiilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.synechron.esg.model.AlternativesCompany;
import org.synechron.esg.alternative.utils.AlternativeUtils;


/**
 * The type Alternatives company builder.
 */
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

    /**
     * With isin alternatives company builder.
     *
     * @param isin the isin
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withISIN(String isin){
        this.isin = isin;
        return this;

    }


    /**
     * With company name alternatives company builder.
     *
     * @param companyName the company name
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withCompanyName(String companyName){
        this.companyName = companyName;
        return this;

    }

    /**
     * With esg combined score alternatives company builder.
     *
     * @param esgCombinedScore the esg combined score
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withESGCombinedScore(Double esgCombinedScore){
        this.esgCombinedScore = AlternativeUtils.formatDouble(esgCombinedScore);
        return this;

    }

    /**
     * With esg score alternatives company builder.
     *
     * @param esgScore the esg score
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withESGScore(Double esgScore){
        this.esgScore = AlternativeUtils.formatDouble(esgScore);
        return this;

    }

    /**
     * With alternatives company builder.
     *
     * @param esgScore the esg score
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder with(Double esgScore){
        this.esgCombinedScore = AlternativeUtils.formatDouble(esgScore);
        return this;

    }

    /**
     * With esg controversies score alternatives company builder.
     *
     * @param esgControversiesScore the esg controversies score
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withESGControversiesScore(Double esgControversiesScore){
        this.esgControversiesScore = AlternativeUtils.formatDouble(esgControversiesScore);
        return this;

    }

    /**
     * With env score alternatives company builder.
     *
     * @param envScore the env score
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withEnvScore(Double envScore){
        this.envScore = AlternativeUtils.formatDouble(envScore);
        return this;

    }

    /**
     * With social score alternatives company builder.
     *
     * @param socialScore the social score
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withSocialScore(Double socialScore){
        this.socialScore = AlternativeUtils.formatDouble(socialScore);
        return this;

    }

    /**
     * With gov score alternatives company builder.
     *
     * @param govScore the gov score
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withGovScore(Double govScore){
        this.govScore = AlternativeUtils.formatDouble(govScore);
        return this;

    }

    /**
     * With sustainalytics total esg score alternatives company builder.
     *
     * @param sustainalyticsTotalEsgScore the sustainalytics total esg score
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withSustainalyticsTotalEsgScore(Double sustainalyticsTotalEsgScore){
        this.sustainalyticsTotalEsgScore = AlternativeUtils.formatDouble(sustainalyticsTotalEsgScore);
        return this;

    }

    /**
     * With sustainalytics env score alternatives company builder.
     *
     * @param sustainalyticsEnvScore the sustainalytics env score
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withSustainalyticsEnvScore(Double sustainalyticsEnvScore){
        this.sustainalyticsEnvScore = AlternativeUtils.formatDouble(sustainalyticsEnvScore);
        return this;

    }


    /**
     * Build alternatives company.
     *
     * @return the alternatives company
     */
    public AlternativesCompany build(){
        return new AlternativesCompany(isin,companyName,esgScore,esgCombinedScore,esgControversiesScore,envScore,socialScore,govScore,sustainalyticsTotalEsgScore,sustainalyticsEnvScore,sustainalyticsSocialScore,sustainalyticsGovScore,country,countryName,isPortfolioCompany,sector,isinType);
    }

    /**
     * With sustainalytics social score alternatives company builder.
     *
     * @param sustainalyticsSocialScore the sustainalytics social score
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withSustainalyticsSocialScore(Double sustainalyticsSocialScore){
        this.sustainalyticsSocialScore = AlternativeUtils.formatDouble(sustainalyticsSocialScore);
        return this;

    }

    /**
     * With sustainalytics gov score alternatives company builder.
     *
     * @param sustainalyticsGovScore the sustainalytics gov score
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withSustainalyticsGovScore(Double sustainalyticsGovScore){
        this.sustainalyticsGovScore = AlternativeUtils.formatDouble(sustainalyticsGovScore);
        return this;

    }

    /**
     * With country alternatives company builder.
     *
     * @param country the country
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withCountry(String country){
        this.country = country;
        return this;
    }

    /**
     * With country name alternatives company builder.
     *
     * @param countryName the country name
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withCountryName(String countryName){
        this.countryName = countryName;
        return this;
    }

    /**
     * With is portfolio company alternatives company builder.
     *
     * @param isPortfolioCompany the is portfolio company
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withIsPortfolioCompany(Boolean isPortfolioCompany){
        this.isPortfolioCompany = isPortfolioCompany;
        return this;
    }

    /**
     * With sector alternatives company builder.
     *
     * @param sector the sector
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withSector(String sector){
        this.sector = sector;
        return this;
    }

    /**
     * With isin type alternatives company builder.
     *
     * @param isinType the isin type
     * @return the alternatives company builder
     */
    public AlternativesCompanyBuilder withIsinType(String isinType){
        this.isinType = isinType;
        return this;

    }
}
