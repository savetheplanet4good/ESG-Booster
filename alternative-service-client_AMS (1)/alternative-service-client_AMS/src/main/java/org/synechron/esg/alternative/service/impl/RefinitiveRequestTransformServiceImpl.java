package org.synechron.esg.alternative.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Refinitive request transform service.
 */
@Service("refinitiveRequestTransformServiceImpl")
public class RefinitiveRequestTransformServiceImpl implements AlternativeRequestTransformService {

    private static final Logger log = LoggerFactory.getLogger(RefinitiveRequestTransformServiceImpl.class);

    @Override
    public AlternativesInvestSuitRequest getAlternativeRequest(List<Company> companies) {

        List<AlternativesInvestSuitRequest> response = new ArrayList<>();
        Map<String, Double> map = new HashMap<>();
        List<Company> companiesWithoutFund = getCompanies(companies.stream().filter(company -> !Constant.FUND.equalsIgnoreCase(company.getIsinType())).collect(Collectors.toList()));
        Double totalWt = companiesWithoutFund.stream().mapToDouble(company-> company.getWt()).sum();
        if(totalWt < 100.00)
            companiesWithoutFund = normalizeWeitages(companiesWithoutFund ,100-totalWt);

        companiesWithoutFund.forEach(company -> {
            map.put(company.getIsin(), BigDecimal.valueOf(company.getHoldingValue()).divide(new BigDecimal(100)).doubleValue());
        });

        return new AlternativesInvestSuitRequest(map, null, null, null, null);
    }

    @Override
    public List<AlternativesCompany> getInvestibleUniverse(List<AlternativesCompany> alternativesCompanyList, InvestableUniverseFilter filter) {
       log.debug("getInvestibleUniverse start ");
       List<AlternativesCompany> alternativesCompanies = alternativesCompanyList.stream().filter(alternative -> alternative.getEnvironmentalScore() > filter.getEnvironmental() &&
                alternative.getGovernenceScore() > filter.getGovernance() &&
                alternative.getSocialScore() > filter.getSocial() &&
                alternative.getEsgCombinedScore() > filter.getEsg()
                //&& filter.getSelectedCountries().contains(alternative.getCountryName())
                //&& filter.getSelectedSectors().contains(alternative.getSector())
                 ).collect(Collectors.toList());

        log.debug(alternativesCompanies.toString());

        return alternativesCompanies;
    }

    @Override
    public Alternatives getAlternativeObject(Company company , int alternativeId) {
        return new Alternatives(alternativeId, company.getIsin(), company.getName(),
                AlternativeUtils.formatDouble(company.getEsgScore()),
                AlternativeUtils.formatDouble(company.getHoldingValue()),
                AlternativeUtils.formatDouble(company.getInfluenceESGCombinedScore()),
                AlternativeUtils.formatDouble(company.getEsgCombinedScore()),
                AlternativeUtils.formatDouble(company.getEnvironmentalScore()),
                AlternativeUtils.formatDouble(company.getSocialScore()),
                AlternativeUtils.formatDouble(company.getGovernenceScore()));
    }

    @Override
    public PortfolioSimulationResponse getPortfolioSimulationResponse(Portfolio portfolio) {
        return new PortfolioStimulationResponseBuilder()
                .withESGScore(portfolio.getEsgScore()).withEnvScore(portfolio.getEnvScore())
                .withSocialScore(portfolio.getSocialScore()).withGovScore(portfolio.getGovScore())
                .withESGCombinedScore(portfolio.getEsgCombinedScore()).withRegions(portfolio.getRegions())
                .withSector(portfolio.getSector()).withCurrency(portfolio.getCurrency())
                .withCountryAllocation(portfolio.getCountryAllocation())
                .withCurrencyAllocation(portfolio.getCurrencyAllocation())
                .build();

    }


}
