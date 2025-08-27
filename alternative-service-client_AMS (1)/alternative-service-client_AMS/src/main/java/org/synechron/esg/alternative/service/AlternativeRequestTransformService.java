package org.synechron.esg.alternative.service;

import org.synechron.esg.model.AlternativesCompany;
import org.synechron.esg.model.Company;
import org.synechron.esg.model.InvestableUniverseFilter;
import org.synechron.esg.model.Portfolio;
import org.synechron.esg.alternative.request.AlternativesInvestSuitRequest;
import org.synechron.esg.alternative.response.Alternatives;
import org.synechron.esg.alternative.response.PortfolioSimulationResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface Alternative request transform service.
 */
public interface AlternativeRequestTransformService {

    /**
     * Gets alternative request.
     *
     * @param companies the companies
     * @return the alternative request
     */
    public AlternativesInvestSuitRequest getAlternativeRequest(List<Company> companies);

    /**
     * Gets investible universe.
     *
     * @param alternativesCompanyList the alternatives company list
     * @param filter                  the filter
     * @return the investible universe
     */
    public List<AlternativesCompany> getInvestibleUniverse(List<AlternativesCompany> alternativesCompanyList, InvestableUniverseFilter filter);

    /**
     * Normalize weitages list.
     *
     * @param companies                    the companies
     * @param totalWtsNonExistentCompanies the total wts non existent companies
     * @return the list
     */
    default List<Company> normalizeWeitages(List<Company> companies, Double totalWtsNonExistentCompanies) {

        //List<Company> portfolioCompanies = portfolio.getCompanies();

        companies.forEach(portfolioCompany -> {
            Double totalWeigtageExcludingNonExistentCompany = 100 - totalWtsNonExistentCompanies;
            Double normalisedWeightage = (portfolioCompany.getWt() / totalWeigtageExcludingNonExistentCompany) * 100;
            System.out.println("ISIN = " + portfolioCompany.getIsin() + "  " + "Old Weightage =" + portfolioCompany.getWt() + " " + "Normalised Weightage =" + normalisedWeightage);
            portfolioCompany.setWt(normalisedWeightage);
            portfolioCompany.setHoldingValue(normalisedWeightage);
        });

        //portfolio.setCompanies(portfolioCompanies);

        return companies;
    }

    /**
     * Get companies list.
     *
     * @param companies the companies
     * @return the list
     */
    default List<Company> getCompanies(List<Company> companies){
        List<Company> response = new ArrayList<>();
        for(Company company: companies){
            response.add( new Company(company.getIsin(),company.getName(),company.getWt(),company.getAmountInvested(),company.getEsgScore(),
                    company.getEnvironmentalScore(),company.getSocialScore(),company.getGovernenceScore(),company.getHoldingValue(),company.getControversyScore(),company.getEsgCombinedScore(),
                    company.getTotalESGCombinedScore(),
                    company.getTotalESGScore(),company.getTotalEnvironmentalScore(),company.getTotalSocialScore(),company.getTotalGovernanceScore(),company.getInfluenceESGCombinedScore(),
                    company.getInfluenceESGSCore(),company.getInfluenceEnvironmentalScore(),company.getInfluenceSocialScore(),company.getInfluenceGovernanceScore(),company.getEnvironmentalFactors(),
                    company.getSocialFactors(),company.getGovernanceFactors(),company.getSustainlyticsEsgScore(),company.getSustainlyticsEnvironmentalScore(),company.getSustainlyticsSocialScore(),
                    company.getSustainlyticsGovernenceScore(),company.getSustainalyticsTotalESGScore(),company.getSustainalyticsTotalEnvironmentalScore(),company.getSustainalyticsTotalSocialScore(),
                    company.getSustainalyticsTotalGovernanceScore(),company.getInfluenceESGScoreForSustainalytics(),company.getInfluenceEnvironmentalScoreForSustainalytics(),
                    company.getInfluenceSocialScoreForSustainalytics(),company.getInfluenceGovernanceScoreForSustainalytics(),company.getSustainlyticsNormalisedScore(),
                    company.getOutlierScore(),company.getEnvOutlierScore(),company.getSocialOutlierScore(),company.getGovOutlierScore(),company.getRefinitivNormalisedScore(),company.getIsOutlier(),company.getIsinType()   ));
        }
        return response;
    }


    /**
     * Gets alternative object.
     *
     * @param company       the company
     * @param alternativeId the alternative id
     * @return the alternative object
     */
    public Alternatives getAlternativeObject(Company company, int alternativeId);

    /**
     * Gets portfolio simulation response.
     *
     * @param portfolio the portfolio
     * @return the portfolio simulation response
     */
    public PortfolioSimulationResponse getPortfolioSimulationResponse(Portfolio portfolio);


}
