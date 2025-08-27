package org.synechron.esg.alternative.service.impl;

import org.springframework.stereotype.Service;
import org.synechron.esg.alternative.constant.Constant;
import org.synechron.esg.model.AlternativesCompany;
import org.synechron.esg.model.Company;
import org.synechron.esg.model.InvestableUniverseFilter;
import org.synechron.esg.model.Portfolio;
import org.synechron.esg.alternative.request.AlternativesInvestSuitRequest;
import org.synechron.esg.alternative.response.Alternatives;
import org.synechron.esg.alternative.response.PortfolioSimulationResponse;
import org.synechron.esg.alternative.response.buiilder.PortfolioStimulationResponseBuilder;
import org.synechron.esg.alternative.service.AlternativeRequestTransformService;
import org.synechron.esg.alternative.utils.AlternativeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Yahoo finance request transform service.
 */
@Service("yahooFinanceRequestTransformServiceImpl")
public class YahooFinanceRequestTransformServiceImpl implements AlternativeRequestTransformService {
    @Override
    public AlternativesInvestSuitRequest getAlternativeRequest(List<Company> companies) {

        List<AlternativesInvestSuitRequest> response = new ArrayList<>();
        Map<String, Double> map = new HashMap<>();
        List<Company> companiesWithoutFund = getCompanies(companies.stream().filter(company -> !Constant.FUND.equalsIgnoreCase(company.getIsinType())).collect(Collectors.toList()));
        Double totalWt = companiesWithoutFund.stream().mapToDouble(company-> company.getWt()).sum();
        if(totalWt < 100.00)
            companiesWithoutFund = normalizeWeitages(companiesWithoutFund ,100-totalWt);

        companiesWithoutFund.forEach(company -> {
            map.put(company.getIsin(), company.getHoldingValue() / 100);
        });

        return new AlternativesInvestSuitRequest(map, null, null, null, null);
    }

    @Override
    public List<AlternativesCompany> getInvestibleUniverse(List<AlternativesCompany> alternativesCompanyList, InvestableUniverseFilter filter) {
        List<AlternativesCompany> alternativesCompanies = alternativesCompanyList.stream().filter(alternative -> alternative.getEnvironmentalScore() < filter.getEnvironmental() &&
                alternative.getGovernenceScore() < filter.getGovernance() &&
                alternative.getSocialScore() < filter.getSocial() &&
                alternative.getEsgCombinedScore() < filter.getEsg() &&
                filter.getSelectedCountries().contains(alternative.getCountryName()) &&
                filter.getSelectedSectors().contains(alternative.getSector())).collect(Collectors.toList());

        return alternativesCompanies;
    }

    @Override
    public Alternatives getAlternativeObject(Company company, int alternativeId) {
        return new Alternatives(alternativeId, company.getIsin(), company.getName(),
                AlternativeUtils.formatDouble(company.getSustainlyticsEsgScore()),
                AlternativeUtils.formatDouble(company.getHoldingValue()),
                AlternativeUtils.formatDouble(company.getInfluenceESGScoreForSustainalytics()),
                AlternativeUtils.formatDouble(company.getSustainlyticsEsgScore()),
                AlternativeUtils.formatDouble(company.getSustainlyticsEnvironmentalScore()),
                AlternativeUtils.formatDouble(company.getSustainlyticsSocialScore()),
                AlternativeUtils.formatDouble(company.getSustainlyticsGovernenceScore()));

    }

    @Override
    public PortfolioSimulationResponse getPortfolioSimulationResponse(Portfolio portfolio) {
        return new PortfolioStimulationResponseBuilder()
                .withESGScore(portfolio.getSustainalyticsCombinedScore()).
                        withEnvScore(portfolio.getSustainalyticsEnvScore())
                .withSocialScore(portfolio.getSustainalyticsSocialScore()).withGovScore(portfolio.getSustainalyticsGovScore())
                .withESGCombinedScore(portfolio.getSustainalyticsCombinedScore()).withRegions(portfolio.getRegions())
                .withSector(portfolio.getSector()).withCurrency(portfolio.getCurrency())
                .withCountryAllocation(portfolio.getCountryAllocation())
                .withCurrencyAllocation(portfolio.getCurrencyAllocation()).build();

    }

}
