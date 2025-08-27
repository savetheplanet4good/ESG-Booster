package org.synechron.portfolio.response.dto.builder;

import org.synechron.esg.model.CountryAllocation;
import org.synechron.esg.model.CurrencyAllocation;
import org.synechron.esg.model.Sector;
import org.synechron.esg.model.PortfolioSimulationResponse;
import org.synechron.portfolio.utils.PortfolioUtils;

import java.util.List;

public class PortfolioStimulationResponseBuilder {

    private Double esgScore;

    private Double envScore;

    private Double socialScore;

    private Double govScore;

    private Double esgCombinedScore;

    private List<String> regions;

    private Sector sector;

    private List<String> currency;

    private CurrencyAllocation currencyAllocation;

    private CountryAllocation countryAllocation;

    public PortfolioStimulationResponseBuilder withESGScore(Double esgScore) {
        this.esgScore = PortfolioUtils.formatDouble(esgScore);
        return this;
    }

    public PortfolioStimulationResponseBuilder withEnvScore(Double envScore) {
        this.envScore = PortfolioUtils.formatDouble(envScore);
        return this;
    }

    public PortfolioStimulationResponseBuilder withSocialScore(Double socialScore) {
        this.socialScore = PortfolioUtils.formatDouble(socialScore);
        return this;
    }

    public PortfolioStimulationResponseBuilder withGovScore(Double govScore) {
        this.govScore = PortfolioUtils.formatDouble(govScore);
        return this;
    }

    public PortfolioStimulationResponseBuilder withESGCombinedScore(Double esgCombinedScore) {
        this.esgCombinedScore = PortfolioUtils.formatDouble(esgCombinedScore);
        return this;
    }

    public PortfolioStimulationResponseBuilder withRegions(List<String> regions) {
        this.regions = regions;
        return this;
    }

    public PortfolioStimulationResponseBuilder withSector(Sector sector) {
        this.sector = sector;
        return this;
    }

    public PortfolioStimulationResponseBuilder withCurrency(List<String> currency) {
        this.currency = currency;
        return this;
    }

    public PortfolioStimulationResponseBuilder withCurrencyAllocation(CurrencyAllocation currencyAllocation){
        this.currencyAllocation = currencyAllocation;
        return this;
    }

    public PortfolioStimulationResponseBuilder withCountryAllocation(CountryAllocation countryAllocation){
        this.countryAllocation = countryAllocation;
        return this;
    }

    public PortfolioSimulationResponse build() {
        return new PortfolioSimulationResponse(esgScore, envScore, socialScore, govScore, esgCombinedScore, regions, sector, currency,currencyAllocation,countryAllocation);
    }


}
