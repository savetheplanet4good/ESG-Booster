package org.synechron.portfolio.service.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.model.*;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.enums.PortfolioTypeEnum;
import org.synechron.portfolio.request.UpdateInvestmentUniverseFilterRequest;
import org.synechron.portfolio.response.dto.*;
import org.synechron.portfolio.response.dto.builder.AlternativesCompanyBuilder;
import org.synechron.portfolio.service.InvestibleUniverseService;
import org.synechron.portfolio.utils.PortfolioUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class InvestibleUniverseServiceImpl implements InvestibleUniverseService {

    private static String RESPONSE ="Response :{}";

    @Autowired
    HazelcastInstance hazelcastInstance;

    @Autowired
    private MasterUniverseServiceProxy masterUniverseServiceProxy;

    @Autowired
    private DataLakeServiceProxy dataLakeProxy;

    private static final Logger log = LoggerFactory.getLogger(InvestibleUniverseServiceImpl.class);


    @Override
    public InvestibleUniverseUIResponse getInvestibleUniverse(String portfolioId) {

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap("portfolios");
        IMap<String, InvestibleUniverseResponse> investibleUniverseResponseIMap = hazelcastInstance.getMap("investableUniverse");
        Portfolio portfolio = dataStore.get(portfolioId);

        InvestibleUniverseResponse investibleUniverseResponseResponseEntity = null;
        InvestibleUniverseUIResponse investibleUniverseUIResponse = new InvestibleUniverseUIResponse();
        InvestibleUniverseFilterUIResponseDto investibleUniverseFilterDto = new InvestibleUniverseFilterUIResponseDto();
        List<AlternativesCompany> filteredCompaniesBasedOnPortfolioIsinType = null;

        if (investibleUniverseResponseIMap.get(portfolioId) == null) {
                ResponseEntity<InvestibleUniverseResponse> investibleUniverseResponse = masterUniverseServiceProxy.getInvestibleUniverse(portfolio);
                if (investibleUniverseResponse.getStatusCode().equals(HttpStatus.OK)) {
                    if (investibleUniverseResponse == null) {

                        log.error("Error occurred while retrieving investable universe data from master-universe service or received null data.");
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while retrieving investable universe data from master-universe service or received null data.");
                    } else {
                        log.info("putting data in cache");
                        investibleUniverseResponseResponseEntity = investibleUniverseResponse.getBody();
                        if(PortfolioTypeEnum.FUND.getValue().equalsIgnoreCase(portfolio.getPortfolioIsinsType())){
                            filteredCompaniesBasedOnPortfolioIsinType = investibleUniverseResponseResponseEntity.getCompanies()
                                    .stream()
                                    .filter(a -> PortfolioTypeEnum.FUND.getValue().equalsIgnoreCase(a.getIsinType()))
                                    .collect(Collectors.toList());

                        }else if(PortfolioTypeEnum.EQUITY.getValue().equalsIgnoreCase(portfolio.getPortfolioIsinsType())){
                            filteredCompaniesBasedOnPortfolioIsinType = investibleUniverseResponseResponseEntity.getCompanies()
                                    .stream()
                                    .filter(a -> PortfolioTypeEnum.EQUITY.getValue().equalsIgnoreCase(a.getIsinType()))
                                    .collect(Collectors.toList());
                        }else{
                            //Throw error
                        }

                        //Set filtered companies
                        investibleUniverseResponseResponseEntity.setCompanies(filteredCompaniesBasedOnPortfolioIsinType);
                        investibleUniverseResponseIMap.put(portfolioId, investibleUniverseResponseResponseEntity);
                    }
                }


        }else {
            log.info("getting data from cache");
            investibleUniverseResponseResponseEntity = investibleUniverseResponseIMap.get(portfolioId);
        }


        //Return data according to the selected data source
        List<InvestableUniverseCompanies> investableUniverseCompanies = new ArrayList<>();
        List<AlternativesCompany> companies = investibleUniverseResponseResponseEntity.getCompanies();

        //Set values in company's object
        for (AlternativesCompany alternativesCompany : companies) {
            InvestableUniverseCompanies investableUniverseCompany = new InvestableUniverseCompanies();

            investableUniverseCompany.setIsin(alternativesCompany.getIsin());
            investableUniverseCompany.setCompanyName(alternativesCompany.getCompanyName());
            if(portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE))
            {
                investableUniverseCompany.setEsgScore(alternativesCompany.getEsgScore());
                investableUniverseCompany.setEsgCombinedScore(alternativesCompany.getEsgCombinedScore());
                investableUniverseCompany.setEsgControversiesScore(alternativesCompany.getEsgControversiesScore());
                investableUniverseCompany.setEnvironmentalScore(alternativesCompany.getEnvironmentalScore());
                investableUniverseCompany.setSocialScore(alternativesCompany.getSocialScore());
                investableUniverseCompany.setGovernenceScore(alternativesCompany.getGovernenceScore());
                investableUniverseCompany.setCountry(alternativesCompany.getCountry());
                investableUniverseCompany.setCountryName(alternativesCompany.getCountryName());
                investableUniverseCompany.setSector(alternativesCompany.getSector());
            }
            else if(portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE))
            {
                investableUniverseCompany.setEsgScore(alternativesCompany.getSustainalyticsTotalEsgScore());
                investableUniverseCompany.setEsgCombinedScore(alternativesCompany.getSustainalyticsTotalEsgScore());
                investableUniverseCompany.setEsgControversiesScore(0.0);
                investableUniverseCompany.setEnvironmentalScore(alternativesCompany.getSustainalyticsEnvScore());
                investableUniverseCompany.setSocialScore(alternativesCompany.getSustainalyticsSocialScore());
                investableUniverseCompany.setGovernenceScore(alternativesCompany.getSustainalyticsGovScore());
                investableUniverseCompany.setCountry(alternativesCompany.getCountry());
                investableUniverseCompany.setCountryName(alternativesCompany.getCountryName());
                investableUniverseCompany.setSector(alternativesCompany.getSector());
            }
            else if(portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.MSCI_INVESTIBLE_UNVIERSE_TYPE))
            {
                investableUniverseCompany.setEsgMsciScore(alternativesCompany.getEsgMsciScore());
                investableUniverseCompany.setFdNr(PortfolioUtilities.normaliseDecimals(alternativesCompany.getFdNr()));
                investableUniverseCompany.setFdA(PortfolioUtilities.normaliseDecimals(alternativesCompany.getFdA()));
                investableUniverseCompany.setFdAA(PortfolioUtilities.normaliseDecimals(alternativesCompany.getFdAA()));
                investableUniverseCompany.setFdAAA(PortfolioUtilities.normaliseDecimals(alternativesCompany.getFdAAA()));
                investableUniverseCompany.setFdB(PortfolioUtilities.normaliseDecimals(alternativesCompany.getFdB()));
                investableUniverseCompany.setFdBB(PortfolioUtilities.normaliseDecimals(alternativesCompany.getFdBB()));
                investableUniverseCompany.setFdBBB(PortfolioUtilities.normaliseDecimals(alternativesCompany.getFdBBB()));
                investableUniverseCompany.setFdCCC(PortfolioUtilities.normaliseDecimals(alternativesCompany.getFdCCC()));
                investableUniverseCompany.setFundEsgScore(PortfolioUtilities.normaliseDecimals(alternativesCompany.getFundEsgScore()));
                investableUniverseCompany.setEsgScore(PortfolioUtilities.normaliseDecimals(alternativesCompany.getFundEsgScore()));
            }
            else
            {
                log.error("Invalid data source.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
            }

            investableUniverseCompany.setIsPortfolioCompany(alternativesCompany.getIsPortfolioCompany());
            investableUniverseCompany.setIsinType(alternativesCompany.getIsinType());

            //Add to list
            investableUniverseCompanies.add(investableUniverseCompany);
        }

        //Set values in filter object
        if(portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)){
            investibleUniverseFilterDto.setEsg(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getEsg());
            investibleUniverseFilterDto.setEnvironmental(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getEnvironmental());
            investibleUniverseFilterDto.setSocial(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getSocial());
            investibleUniverseFilterDto.setGovernance(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getGovernance());
            investibleUniverseFilterDto.setSelectedCountries(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getSelectedCountries());
            investibleUniverseFilterDto.setSelectedSectors(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getSelectedSectors());
        } else if(portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE)) {
            investibleUniverseFilterDto.setEsg(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getSusEsg());
            investibleUniverseFilterDto.setEnvironmental(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getSusEnvironmental());
            investibleUniverseFilterDto.setSocial(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getSusSocial());
            investibleUniverseFilterDto.setGovernance(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getSusGovernance());
            investibleUniverseFilterDto.setSelectedCountries(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getSelectedCountries());
            investibleUniverseFilterDto.setSelectedSectors(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getSelectedSectors());
        } else if(portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.MSCI_INVESTIBLE_UNVIERSE_TYPE)) {
            investibleUniverseFilterDto.setEsg(investibleUniverseResponseResponseEntity.getInvestibleUniverseFilter().getFundEsgScore());
        } else{
            log.error("Invalid data source.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
        }

        //Set values in response object
        investibleUniverseUIResponse.setCompanies(investableUniverseCompanies);
        investibleUniverseUIResponse.setInvestibleUniverseFilter(investibleUniverseFilterDto);

        return investibleUniverseUIResponse;
    }

    @Override
    public UpdateInvestibleUniverseResponse updateInvestibleFilter(UpdateInvestmentUniverseFilterRequest request, String portfolioId) {

        ResponseEntity<UpdateInvestibleUniverseResponse> updateInvestibleUniverseResponseResponseEntity = masterUniverseServiceProxy.updateInvetibleUniverseFilter(portfolioId, request);
        if (updateInvestibleUniverseResponseResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            if (updateInvestibleUniverseResponseResponseEntity.getBody() == null) {
                log.error("Error occurred while updating investable universe data from master-universe service or received null data.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while updating investable universe data from master-universe service or received null data.");
            }
        }

        return updateInvestibleUniverseResponseResponseEntity.getBody();
    }


    public List<AlternativesCompany> getCompanyResponse(Portfolio portfolio) {

        List<AlternativesCompany> alternativeCompanies = null;
        List<CompanyDto> responseList = new ArrayList<>();
        Optional<ResponseEntity<List<CompanyESGScore>>> dataLakeresponse = Optional.empty();
        Optional<IMap<String, AlternativesCompany>> dataStore = Optional.of(hazelcastInstance.getMap("alternativeComapnies"));

        if ( dataStore.get().size() > 0) {
            alternativeCompanies = (List<AlternativesCompany>) dataStore.get().values();
            /* response.setData(alternativeCompanies); */
        } else {
            // dataStore.put(finalPortfolio.getPortfolioId(), finalPortfolio);
            try {
                dataLakeresponse = Optional.of(dataLakeProxy.getAllInvestibleCompanies());
                log.debug(RESPONSE, dataLakeresponse.toString());
                log.debug("Response status:{}", dataLakeresponse.get().getStatusCode());
                log.debug(RESPONSE, dataLakeresponse.get().getHeaders());
                log.debug(RESPONSE, dataLakeresponse.get().getBody());

                if (dataLakeresponse.get().getBody() != null && dataLakeresponse.get().getStatusCode().equals(HttpStatus.OK) && !dataLakeresponse.get().getBody().isEmpty()) {
                    Optional<List<CompanyESGScore>> allInvestibleUniverseCompanies = Optional.of((List<CompanyESGScore>) dataLakeresponse.get().getBody());
                    alternativeCompanies = allInvestibleUniverseCompanies.get().stream().filter(a -> !a.getIsinType().equalsIgnoreCase(PortfolioTypeEnum.FUND.getValue()))
                            .map(c -> new AlternativesCompanyBuilder()
                                    .withISIN(c.getIsin())
                                    .withCompanyName(c.getInstrumentName())
                                    .withESGScore(c.getEsgScore())
                                    .withESGCombinedScore(c.getEsgCombinedScore())
                                    .withEnvScore(c.getEnvironmentPillarScore())
                                    .withSocialScore(c.getSocialPillarScore())
                                    .withGovScore(c.getGovernancePillarScore())
                                    .withSustainalyticsTotalEsgScore(c.getSustainalyticsTotalEsgScore())
                                    .withSustainalyticsEnvScore(c.getSustainalyticsEnvironmentScore())
                                    .withSustainalyticsSocialScore(c.getSustainalyticsSocialScore())
                                    .withSustainalyticsGovScore(c.getSustainalyticsGovernanceScore())
                                    .withCountry(c.getCountry())
                                    .withCountryName(c.getCountryName())
                                    .withSector(c.getSectors())
                                    .withIsinType(c.getIsinType())
                                    .build()
                            ).collect(Collectors.toList());

                    if (alternativeCompanies != null && alternativeCompanies.size() > 0) {
                        for (AlternativesCompany alternativeCompany : alternativeCompanies) {
                            dataStore.get().put(alternativeCompany.getIsin(), alternativeCompany);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Error {}", e);
                return alternativeCompanies;
            }
        }

        //Mark the companies which belong to the portfolio as TRUE
        List<String> isinList = PortfolioUtils.getISINList(portfolio);
        for (AlternativesCompany altComp : alternativeCompanies) {
            if (isinList.contains(altComp.getIsin())) {
                altComp.setIsPortfolioCompany(Boolean.TRUE);
            } else {
                altComp.setIsPortfolioCompany(Boolean.FALSE);
            }
        }

        return alternativeCompanies;
    }
}
