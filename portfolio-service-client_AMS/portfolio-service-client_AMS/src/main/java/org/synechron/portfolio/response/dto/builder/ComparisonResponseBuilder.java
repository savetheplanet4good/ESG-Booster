package org.synechron.portfolio.response.dto.builder;

import org.springframework.data.mongodb.core.mapping.Field;
import org.synechron.esg.model.CountryAllocation;
import org.synechron.esg.model.CurrencyAllocation;
import org.synechron.esg.model.Sector;
import org.synechron.portfolio.response.dto.ComparisonResponse;
import org.synechron.portfolio.utils.PortfolioUtils;

import java.util.List;

public class ComparisonResponseBuilder {

    @Field(name="sector")
    private Sector sector;

    @Field(name="regions")
    private List<String> regions;

    private Double esgScore;

    private Double envScore;

    private Double socialScore;

    private Double govScore;

    private Double esgCombinedScore;

    private List<String> currency;

    private CurrencyAllocation currencyAllocation;

    private CountryAllocation countryAllocation;

    public ComparisonResponseBuilder withSector(Sector sector) {
        this.sector = sector;
        return this;
    }

    public ComparisonResponseBuilder withRegions(List<String> regions){
        this.regions =regions;
        return this;
    }

    public ComparisonResponseBuilder withESGSCore(Double esgScore){
        this.esgScore = PortfolioUtils.formatDouble(esgScore);
        return this;
    }

    public ComparisonResponseBuilder withEnvSCore(Double envScore){
        this.envScore =PortfolioUtils.formatDouble(envScore);
        return this;
    }

    public ComparisonResponseBuilder withSocialScore(Double socialScore){
        this.socialScore =PortfolioUtils.formatDouble(socialScore);
        return this;
    }
    public ComparisonResponseBuilder withGovScore(Double govScore){
        this.govScore =PortfolioUtils.formatDouble(govScore);
        return this;
    }
    public ComparisonResponseBuilder withESGSCombinedCore(Double esgCombinedScore){
        this.esgCombinedScore =PortfolioUtils.formatDouble(esgCombinedScore);
        return this;
    }
    public ComparisonResponseBuilder withCurrency(List<String> currency){
        this.currency = currency;
        return this;
    }

    public ComparisonResponseBuilder withCurrencyAllocation(CurrencyAllocation currencyAllocation){
       this.currencyAllocation = currencyAllocation;
        return this;
    }

    public ComparisonResponseBuilder withCountryAllocation(CountryAllocation countryAllocation){
        this.countryAllocation = countryAllocation;
        return this;
    }

    public ComparisonResponse build(){
        return new ComparisonResponse(sector,regions,esgScore,envScore,socialScore,govScore,esgCombinedScore,currency,currencyAllocation,countryAllocation);
    }
}
