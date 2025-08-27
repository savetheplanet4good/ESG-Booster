package org.synechron.esg.alternative.response.buiilder;
import org.synechron.esg.model.CountryAllocation;
import org.synechron.esg.model.CurrencyAllocation;
import org.synechron.esg.model.Sector;
import org.synechron.esg.alternative.response.PortfolioSimulationResponse;
import org.synechron.esg.alternative.utils.AlternativeUtils;

import java.util.List;

/**
 * The type Portfolio stimulation response builder.
 */
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

    /**
     * With esg score portfolio stimulation response builder.
     *
     * @param esgScore the esg score
     * @return the portfolio stimulation response builder
     */
    public PortfolioStimulationResponseBuilder withESGScore(Double esgScore) {
        this.esgScore = AlternativeUtils.formatDouble(esgScore);
        return this;
    }

    /**
     * With env score portfolio stimulation response builder.
     *
     * @param envScore the env score
     * @return the portfolio stimulation response builder
     */
    public PortfolioStimulationResponseBuilder withEnvScore(Double envScore) {
        this.envScore = AlternativeUtils.formatDouble(envScore);
        return this;
    }

    /**
     * With social score portfolio stimulation response builder.
     *
     * @param socialScore the social score
     * @return the portfolio stimulation response builder
     */
    public PortfolioStimulationResponseBuilder withSocialScore(Double socialScore) {
        this.socialScore = AlternativeUtils.formatDouble(socialScore);
        return this;
    }

    /**
     * With gov score portfolio stimulation response builder.
     *
     * @param govScore the gov score
     * @return the portfolio stimulation response builder
     */
    public PortfolioStimulationResponseBuilder withGovScore(Double govScore) {
        this.govScore = AlternativeUtils.formatDouble(govScore);
        return this;
    }

    /**
     * With esg combined score portfolio stimulation response builder.
     *
     * @param esgCombinedScore the esg combined score
     * @return the portfolio stimulation response builder
     */
    public PortfolioStimulationResponseBuilder withESGCombinedScore(Double esgCombinedScore) {
        this.esgCombinedScore = AlternativeUtils.formatDouble(esgCombinedScore);
        return this;
    }

    /**
     * With regions portfolio stimulation response builder.
     *
     * @param regions the regions
     * @return the portfolio stimulation response builder
     */
    public PortfolioStimulationResponseBuilder withRegions(List<String> regions) {
        this.regions = regions;
        return this;
    }

    /**
     * With sector portfolio stimulation response builder.
     *
     * @param sector the sector
     * @return the portfolio stimulation response builder
     */
    public PortfolioStimulationResponseBuilder withSector(Sector sector) {
        this.sector = sector;
        return this;
    }

    /**
     * With currency portfolio stimulation response builder.
     *
     * @param currency the currency
     * @return the portfolio stimulation response builder
     */
    public PortfolioStimulationResponseBuilder withCurrency(List<String> currency) {
        this.currency = currency;
        return this;
    }

    /**
     * With currency allocation portfolio stimulation response builder.
     *
     * @param currencyAllocation the currency allocation
     * @return the portfolio stimulation response builder
     */
    public PortfolioStimulationResponseBuilder withCurrencyAllocation(CurrencyAllocation currencyAllocation){
        this.currencyAllocation = currencyAllocation;
        return this;
    }

    /**
     * With country allocation portfolio stimulation response builder.
     *
     * @param countryAllocation the country allocation
     * @return the portfolio stimulation response builder
     */
    public PortfolioStimulationResponseBuilder withCountryAllocation(CountryAllocation countryAllocation){
        this.countryAllocation = countryAllocation;
        return this;
    }

    /**
     * Build portfolio simulation response.
     *
     * @return the portfolio simulation response
     */
    public PortfolioSimulationResponse build() {
        return new PortfolioSimulationResponse(esgScore, envScore, socialScore, govScore, esgCombinedScore, regions, sector, currency,currencyAllocation,countryAllocation);
    }


}
