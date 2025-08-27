package org.synechron.portfolio.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.model.*;
import org.synechron.portfolio.application_settings.service.ApplicationComponentSettingsService;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.csv.CompanyModel;
import org.synechron.portfolio.dto.DailyESGScoreRequest;
import org.synechron.portfolio.enums.PortfolioTypeEnum;
import org.synechron.portfolio.error.FileValidationError;
import org.synechron.portfolio.request.ClonePortfolioRequest;
import org.synechron.portfolio.request.NewsSentimentRequest;
import org.synechron.portfolio.response.dto.*;
import org.synechron.portfolio.response.dto.builder.CompanyDtoBuilder;
import org.synechron.portfolio.response.dto.builder.ComparisonResponseBuilder;
import org.synechron.portfolio.response.dto.builder.PortfolioDtoBuilder;
import org.synechron.portfolio.response.history.*;
import org.synechron.portfolio.service.CalculationService;
import org.synechron.portfolio.service.PortfolioService;
import org.synechron.portfolio.utils.PortfolioUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;


@Service
public class PortfolioServiceImpl implements PortfolioService {

    private static Object NULL = null;
    private static String CSV_EXT = "CSV";
    private static Double MAX_WEIGHTAGE = 100.00;
    private static Double MIN_WEIGTHAGE = 99.51;
    private static String PORTFOLIOS = "portfolios";
    private static String RECORD = "Record ";

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.user}")
    private String minioUser;

    @Value("${minio.password}")
    private String minioPassword;

    @Value("${default.file.path}")
    private String filePath;

    private static final Logger log = LoggerFactory.getLogger(PortfolioServiceImpl.class);

    @Autowired
    HazelcastInstance hazelcastInstance;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private NewsServiceProxy newsServiceProxy;
    
	@Autowired
	private DataLakeServiceProxy dataLakeServiceProxy;

    @Autowired
    private ApplicationComponentSettingsService applicationComponentSettingsService;

    @Override
    public PortfolioListResponse getAllPortfolios() throws FileValidationError, Exception {

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);

        //Load the full cache as when the cache ttl elapse cache is empty even though db contains portfolios
        dataStore.loadAll(Boolean.TRUE);

        //Check if cache still empty
        if (dataStore.values().isEmpty()) {

            //Getting default portfolios
            List<Portfolio> defaultPortfolioList = getDefaultPortfolio(Constant.DEFAULT_PORTFOLIOS_REF_SUST_MAP);

            //Insert into database
            for (Portfolio portfolio : defaultPortfolioList)
                insertPortfolio(portfolio);
        }
        PortfolioListResponse response = convertToDto(new ArrayList<Portfolio>(dataStore.values()));

        return response;
    }

    //
    // Convert to Response DTO.
    //
    private PortfolioListResponse convertToDto(List<Portfolio> portfolios) {

        List<PortfolioListDto> list = new ArrayList<>();
        Collections.sort(portfolios, Comparator.comparing(Portfolio::getCreatedDate).reversed());
        for (Portfolio portfolio : portfolios) {

            if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)){
                list.add(new PortfolioListDto(portfolio.getPortfolioId(),
                        portfolio.getPortfolioName(),
                        portfolio.getCompanies().size(),
                        PortfolioUtils.formatDouble(portfolio.getEsgCombinedScore()),
                        PortfolioUtils.formatDouble(portfolio.getEsgScore()),
                        PortfolioUtils.formatDouble(portfolio.getEnvScore()),
                        PortfolioUtils.formatDouble(portfolio.getSocialScore()),
                        PortfolioUtils.formatDouble(portfolio.getGovScore()),
                        PortfolioUtils.formatDouble(portfolio.getContraversyScore()),
                        portfolio.getPortfolioIsinsType(),
                        portfolio.getEsgMsciScore()));
            }else if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE)){
                list.add(new PortfolioListDto(portfolio.getPortfolioId(),
                        portfolio.getPortfolioName(),
                        portfolio.getCompanies().size(),
                        PortfolioUtils.formatDouble(portfolio.getSustainalyticsCombinedScore()),
                        PortfolioUtils.formatDouble(portfolio.getSustainalyticsCombinedScore()),
                        PortfolioUtils.formatDouble(portfolio.getSustainalyticsEnvScore()),
                        PortfolioUtils.formatDouble(portfolio.getSustainalyticsSocialScore()),
                        PortfolioUtils.formatDouble(portfolio.getSustainalyticsGovScore()),
                        PortfolioUtils.formatDouble(0.0),
                        portfolio.getPortfolioIsinsType(),
                        portfolio.getEsgMsciScore()));
            }else if (PortfolioTypeEnum.FUND.getValue().equalsIgnoreCase(portfolio.getPortfolioIsinsType()) && portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.MSCI_INVESTIBLE_UNVIERSE_TYPE)){
                list.add(new PortfolioListDto(portfolio.getPortfolioId(),
                        portfolio.getPortfolioName(),
                        portfolio.getCompanies().size(),
                        PortfolioUtils.formatDouble(portfolio.getFundEsgScore()),
                        null,
                        null,
                        null,
                        null,
                        null,
                        portfolio.getPortfolioIsinsType(),
                        portfolio.getEsgMsciScore()));
            }else{
                log.error("Invalid data source.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
            }
        }
        return new PortfolioListResponse(list);
    }

    //
    //  load default portfolio
    //

    private List<Portfolio> getDefaultPortfolio(Map<String, List<String>> defaultPortfolioDetails) throws FileValidationError, Exception {
        List<Portfolio> defaultPortfolios = new ArrayList<>();
        try {
            for (Map.Entry<String, List<String>> entry : defaultPortfolioDetails.entrySet()) {
                MultipartFile file = getDefaultPortfolioCSV(entry.getKey());
                List<Company> calculatedCompanyDataList = processUploadedPortfolioData(file, file.getOriginalFilename());
                Portfolio portfolioToUpload = createPortfolio(entry.getValue().get(0), entry.getValue().get(1), Constant.PORTFOLIO_TYPE_DEFAULT, entry.getValue().get(2), calculatedCompanyDataList, file.getOriginalFilename());
                defaultPortfolios.add(portfolioToUpload);
            }
        } catch (FileValidationError fileValidationError) {
            throw fileValidationError;
        } catch (ResponseStatusException responseStatusException) {
            throw responseStatusException;
        } catch (Exception exception) {
            throw new Exception("Error occurred while validating the file.");
        }
        return defaultPortfolios;
    }

    @Override
    public CreatePortfolioResponse insertPortfolio(Portfolio portfolio) throws Exception {

        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        CreatePortfolioResponse response = new CreatePortfolioResponse(HttpStatus.OK, "Portfolio uploaded successfully.", null);
        RestResponse calculationResponse = calculationService.calculateESGforPortfolio(portfolio);
        if (calculationResponse.getStatus().equals(HttpStatus.OK)) {
            Portfolio finalPortfolio = (Portfolio) calculationResponse.getData();

            if(applicationComponentSettingsMap.get(Constant.IS_NEWS_ENABLED).getValue().equals(Boolean.TRUE) && PortfolioTypeEnum.EQUITY.getValue().equalsIgnoreCase(finalPortfolio.getPortfolioIsinsType())) {
                //Get news sentiment score for portfolio companies
                List<String> isinList = PortfolioUtils.getISINList(finalPortfolio);
                Map<String, Double> newsSentimentScoreMap = newsServiceProxy.getNewsSentimentScore(new NewsSentimentRequest(isinList, Constant.NEWS_SENTIMENT_FOR_NO_OF_MONTHS)).getBody();
                finalPortfolio = mapNewsSentimentScoreToCompanies(finalPortfolio, newsSentimentScoreMap);
            }

            IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
            dataStore.put(finalPortfolio.getPortfolioId(), finalPortfolio);

        } else {
            response.setMessage(calculationResponse.getMessage());
            response.setStatus(calculationResponse.getStatus());
            Map<String, String> errormap = new HashMap<>();
            errormap.put("error", calculationResponse.getError().toString());
            response.setErrorMap(errormap);

        }

        return response;
    }

    public Portfolio mapNewsSentimentScoreToCompanies(Portfolio portfolio, Map<String, Double> newsSentimentScoreMap){
        List<Company> companies = portfolio.getCompanies();
        for (Company company : companies) {
            company.setNewsSentimentScore(PortfolioUtilities.normaliseDecimals(newsSentimentScoreMap.get(company.getIsin())));
        }
        portfolio.setCompanies(companies);
        return portfolio;
    }

    @Override
    public UpdatePortfolioResponse updatePortfolio(Portfolio portfolio, String operation) {
        HttpStatus status;
        String message;
        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
        try {
            dataStore.lock(portfolio.getPortfolioId());
            Portfolio updatedPortfolio = dataStore.put(portfolio.getPortfolioId(), portfolio, 1200, TimeUnit.SECONDS);
            if (updatedPortfolio != null) {
                if(operation.equalsIgnoreCase(Constant.UPDATE_NAME_PORTFOLIO)){
                    message = "Portfolio Renamed Successfully.";
                }else if(operation.equalsIgnoreCase(Constant.UPDATE_INVESTIBLE_UNIVERSE_PORTFOLIO)){
                	if(portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE))
                	{
                		message = "Portfolio switched to Data Source 1 Scoring Methodology.";
                	}
                	else if(portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE))
                	{
                		message = "Portfolio switched to Data Source 2 Scoring Methodology.";
                	}                		
                	else
                	{
                		message="Portfolio switching failed.";
                	}
                		
                }else{
                    message = "Portfolio Updated Successfully.";
                }
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.BAD_REQUEST;
                message = "Portfolio Not Present.";
            }
        } catch (Exception exception) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Exception occurred while updating the portfolio.";
        } finally {
            dataStore.unlock(portfolio.getPortfolioId());
        }
        return new UpdatePortfolioResponse(status, message);
    }

    @Override
    public PortfolioDto getPortfolio(String portfolioId) throws IOException {

        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
        Portfolio portfolio = dataStore.get(portfolioId);
        int outLierCount = 0;
        if (portfolio.getCompanies() != null && portfolio.getCompanies().size() > 0) {
            //outLierCount=portfolio.getCompanies().stream().filter(c->c.getIsOutlier().equals(Boolean.TRUE)).collect(Collectors.toList()).size();
            outLierCount = portfolio.getCompanies().stream().filter(c -> c.getOutlierScore() != 0).collect(Collectors.toList()).size();
        }
        if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)) {
            return new PortfolioDtoBuilder().withPortfolioId(portfolioId)
                    .withPortfolioName(portfolio.getPortfolioName())
                    .withPortfolioType(portfolio.getPortfolioType())
                    .withInvestableUniverseType(portfolio.getInvestableUniverseType())
                    .withCombinedESGScore(portfolio.getEsgCombinedScore())
                    .withESGScore(portfolio.getEsgScore())
                    .withGovScore(portfolio.getGovScore())
                    .withEnvScore(portfolio.getEnvScore())
                    .withSocialScore(portfolio.getSocialScore())
                    .withComponyCount(portfolio.getCompanies().size())
                    .withOutLierCount(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ? outLierCount : 0)
                    .withFund(portfolio.getFund())
                    .withEquity(portfolio.getEquity())
                    .withCalculationType(portfolio.getCalculationType())
                    .withPortfolioIsinsType(portfolio.getPortfolioIsinsType())
                    .build();
        } else if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE)) {
            return new PortfolioDtoBuilder().withPortfolioId(portfolioId)
                    .withPortfolioName(portfolio.getPortfolioName())
                    .withPortfolioType(portfolio.getPortfolioType())
                    .withInvestableUniverseType(portfolio.getInvestableUniverseType())
                    .withCombinedESGScore(portfolio.getSustainalyticsCombinedScore())
                    .withESGScore(portfolio.getSustainalyticsCombinedScore())
                    .withGovScore(portfolio.getSustainalyticsGovScore())
                    .withEnvScore(portfolio.getSustainalyticsEnvScore())
                    .withSocialScore(portfolio.getSustainalyticsSocialScore())
                    .withComponyCount(portfolio.getCompanies().size())
                    .withOutLierCount(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ? outLierCount : 0)
                    .withFund(portfolio.getFund())
                    .withEquity(portfolio.getEquity())
                    .withCalculationType(portfolio.getCalculationType())
                    .withPortfolioIsinsType(portfolio.getPortfolioIsinsType())
                    .build();
        } else if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.MSCI_INVESTIBLE_UNVIERSE_TYPE)) {
            return new PortfolioDtoBuilder()
                    .withPortfolioId(portfolioId)
                    .withPortfolioName(portfolio.getPortfolioName())
                    .withPortfolioType(portfolio.getPortfolioType())
                    .withComponyCount(portfolio.getCompanies().size())
                    .withInvestableUniverseType(portfolio.getInvestableUniverseType())
                    .withCalculationType(portfolio.getCalculationType())
                    .withPortfolioIsinsType(portfolio.getPortfolioIsinsType())
                    .withESGScore(portfolio.getFundEsgScore())
                    .withEsgMsciScore(portfolio.getEsgMsciScore())
                    .build();
        } else {
            return new PortfolioDto();
        }
    }

    @Override
    public DeletePortfolioResponse deletePortfolioBy(String portfolioId) {
        HttpStatus status;
        String message;

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
        Portfolio portfolio = dataStore.remove(portfolioId);
        if (portfolio != null) {
            status = HttpStatus.OK;
            message = "Portfolio Deleted Successfully.";
        } else {
            status = HttpStatus.BAD_REQUEST;
            message = "Portfolio Not Present.";
        }
        return new DeletePortfolioResponse(status, message);
    }

    @Override
    public void evictAll() {
        // TODO Auto-generated method stub
        IMap<String, Portfolio> dataStore =
                hazelcastInstance.getMap(PORTFOLIOS);

        dataStore.evictAll();
    }

    @Override
    public CompaniesResponse getCompanies(String portfolioId) throws IOException {

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
        Portfolio portfolio = dataStore.get(portfolioId);

        return convertToCompaniesDto(portfolio);
    }

    @Override
    public CompaniesResponse getCompanies() {
        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);

        List<CompanyESGScore> companies = dataLakeServiceProxy.getCompanies().getBody();
        return convertToCompaniesDto(companies);
    }

    @Override
    public CompanyDto getCompany(String portfolioId, String isin) throws IOException {

        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
        Portfolio portfolio = dataStore.get(portfolioId);
        Optional<List<Company>> companies = Optional.of(portfolio.getCompanies());

            Company company = companies.get().stream().filter(filteredCompany -> filteredCompany.getIsin().equals(isin)).findFirst().get();

            if (Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE.equalsIgnoreCase(portfolio.getInvestableUniverseType())) {
                return new CompanyDtoBuilder().withISIN(company.getIsin())
                        .withName(company.getName()).withESGScore(company.getEsgScore())
                        .withEnvScore(company.getEnvironmentalScore())
                        .withSocialScore(company.getSocialScore())
                        .withGovScore(company.getGovernenceScore())
                        .withESGCombinedScore(company.getEsgCombinedScore())
                        .withControversyScore(company.getControversyScore())
                        .withTotalEnvironmentalScore(company.getTotalEnvironmentalScore())
                        .withTotalESGCombinedScore(company.getTotalESGCombinedScore())
                        .withTotalESGScore(company.getTotalESGScore())
                        .withTotalGovernanceScore(company.getTotalGovernanceScore())
                        .withTotalSocialScore(company.getTotalSocialScore())
                        .withInfluenceEnvironmentalScore(company.getInfluenceEnvironmentalScore())
                        .withInfluenceESGCombinedScore(company.getInfluenceESGCombinedScore())
                        .withInfluenceESGSCore(company.getInfluenceESGSCore())
                        .withInfluenceGovernanceScore(company.getInfluenceGovernanceScore())
                        .withInfluenceSocialScore(company.getInfluenceSocialScore())
                        .withEnvironmentalFactors(company.getEnvironmentalFactors())
                        .withSocialFactors(company.getSocialFactors())
                        .withGovernanceFactors(company.getGovernanceFactors())
                        .withHoldingValue(company.getHoldingValue())
                        .withRefinitivNormalisedScore(company.getRefinitivNormalisedScore())
                        .withSustainalyticsNormalisedScore(company.getSustainlyticsNormalisedScore())
                        .withRefinitivESGCombinedScore(company.getEsgCombinedScore())
                        .withSustainalyticsESGCombinedScore(company.getSustainlyticsEsgScore())
                        .withOutLierScore(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ? company.getOutlierScore() : 0)
                        .withIsOutLier(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ? company.getIsOutlier() : Boolean.FALSE)
                        .withIsinType(company.getIsinType())
                        .withSector(company.getSector())
                        .build();
            } else if (Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE.equalsIgnoreCase(portfolio.getInvestableUniverseType())) {
                return new CompanyDtoBuilder().withISIN(company.getIsin())
                        .withName(company.getName()).withESGScore(company.getSustainlyticsEsgScore())
                        .withEnvScore(company.getSustainlyticsEnvironmentalScore())
                        .withSocialScore(company.getSustainlyticsSocialScore())
                        .withGovScore(company.getSustainlyticsGovernenceScore())
                        .withESGCombinedScore(company.getSustainlyticsEsgScore())
                        .withControversyScore(0.00)
                        .withTotalEnvironmentalScore(company.getSustainalyticsTotalEnvironmentalScore())
                        .withTotalESGCombinedScore(company.getSustainalyticsTotalESGScore())
                        .withTotalESGScore(company.getSustainalyticsTotalESGScore())
                        .withTotalGovernanceScore(company.getSustainalyticsTotalGovernanceScore())
                        .withTotalSocialScore(company.getSustainalyticsTotalSocialScore())
                        .withInfluenceEnvironmentalScore(company.getInfluenceEnvironmentalScoreForSustainalytics())
                        .withInfluenceESGCombinedScore(company.getInfluenceESGScoreForSustainalytics())
                        .withInfluenceESGSCore(company.getInfluenceESGScoreForSustainalytics())
                        .withInfluenceGovernanceScore(company.getInfluenceGovernanceScoreForSustainalytics())
                        .withInfluenceSocialScore(company.getInfluenceSocialScoreForSustainalytics())
                        .withEnvironmentalFactors(null)
                        .withSocialFactors(null)
                        .withGovernanceFactors(null)
                        .withHoldingValue(company.getHoldingValue())
                        .withRefinitivNormalisedScore(company.getRefinitivNormalisedScore())
                        .withSustainalyticsNormalisedScore(company.getSustainlyticsNormalisedScore())
                        .withRefinitivESGCombinedScore(company.getEsgCombinedScore())
                        .withSustainalyticsESGCombinedScore(company.getSustainlyticsEsgScore())
                        .withOutLierScore(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE)  ? company.getOutlierScore() : 0)
                        .withIsOutLier(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ? company.getIsOutlier() : Boolean.FALSE)
                        .withIsinType(company.getIsinType())
                        .withSector(company.getSector())
                        .build();
            } else if (Constant.MSCI_INVESTIBLE_UNVIERSE_TYPE.equalsIgnoreCase(portfolio.getInvestableUniverseType())) {
                return new CompanyDtoBuilder()
                        .withISIN(company.getIsin())
                        .withName(company.getName())
                        .withHoldingValue(company.getHoldingValue())
                        .withIsinType(company.getIsinType())
                        .withEsgMsciScore(company.getEsgMsciScore())
                        .withFdNr(PortfolioUtilities.normaliseDecimals(company.getFdNr()))
                        .withFdA(PortfolioUtilities.normaliseDecimals(company.getFdA()))
                        .withFdAA(PortfolioUtilities.normaliseDecimals(company.getFdAA()))
                        .withFdAAA(PortfolioUtilities.normaliseDecimals(company.getFdAAA()))
                        .withFdB(PortfolioUtilities.normaliseDecimals(company.getFdB()))
                        .withFdBB(PortfolioUtilities.normaliseDecimals(company.getFdBB()))
                        .withFdBBB(PortfolioUtilities.normaliseDecimals(company.getFdBBB()))
                        .withFdCCC(PortfolioUtilities.normaliseDecimals(company.getFdCCC()))
                        .withFundEsgScore(PortfolioUtilities.normaliseDecimals(company.getFundEsgScore()))
                        .withTotalFundEsgScore(PortfolioUtilities.normaliseDecimals(company.getTotalFundEsgScore()))
                        .withSustainalyticsESGCombinedScore(PortfolioUtilities.normaliseDecimals(company.getSustainlyticsEsgScore()))
                        .build();
            }else {
                return new CompanyDto();
            }
    }

    private CompaniesResponse convertToCompaniesDto(Portfolio portfolio) throws IOException {

        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        List<CompanyDto> responseList = new ArrayList<>();
        List<Company> companies = portfolio.getCompanies();

        for (Company company : companies) {
            if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)) {
                responseList.add(new CompanyDtoBuilder().withISIN(company.getIsin())
                        .withName(company.getName()).withESGScore(company.getEsgScore())
                        .withEnvScore(company.getEnvironmentalScore())
                        .withSocialScore(company.getSocialScore())
                        .withGovScore(company.getGovernenceScore())
                        .withESGCombinedScore(company.getEsgCombinedScore())
                        .withControversyScore(company.getControversyScore())
                        .withTotalEnvironmentalScore(company.getTotalEnvironmentalScore())
                        .withTotalESGCombinedScore(company.getTotalESGCombinedScore())
                        .withTotalESGScore(company.getTotalESGScore())
                        .withTotalGovernanceScore(company.getTotalGovernanceScore())
                        .withTotalSocialScore(company.getTotalSocialScore())
                        .withInfluenceEnvironmentalScore(company.getInfluenceEnvironmentalScore())
                        .withInfluenceESGCombinedScore(company.getInfluenceESGCombinedScore())
                        .withInfluenceESGSCore(company.getInfluenceESGSCore())
                        .withInfluenceGovernanceScore(company.getInfluenceGovernanceScore())
                        .withInfluenceSocialScore(company.getInfluenceSocialScore())
                        .withEnvironmentalFactors(company.getEnvironmentalFactors())
                        .withSocialFactors(company.getSocialFactors())
                        .withGovernanceFactors(company.getGovernanceFactors())
                        .withHoldingValue(company.getHoldingValue())
                        .withRefinitivNormalisedScore(company.getRefinitivNormalisedScore())
                        .withSustainalyticsNormalisedScore(company.getSustainlyticsNormalisedScore())
                        .withRefinitivESGCombinedScore(company.getEsgCombinedScore())
                        .withSustainalyticsESGCombinedScore(company.getSustainlyticsEsgScore())
                        .withOutLierScore(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ? company.getOutlierScore() : 0)
                        .withIsOutLier(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ? company.getIsOutlier()  : Boolean.FALSE)
                        .withEnvOutLierScore(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ? company.getEnvOutlierScore() : 0)
                        .withSocialOutLierScore(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ? company.getSocialOutlierScore() : 0)
                        .withGovOutLierScore(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ? company.getGovOutlierScore() : 0)
                        .withIsinType(company.getIsinType())
                        .withNewsSentimentScore(applicationComponentSettingsMap.get(Constant.IS_NEWS_ENABLED).getValue().equals(Boolean.TRUE) ? company.getNewsSentimentScore() : null)
                        .withSector(company.getSector())
                        .build());
            } else if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE)) {
                responseList.add(new CompanyDtoBuilder().withISIN(company.getIsin())
                        .withName(company.getName()).withESGScore(company.getSustainlyticsEsgScore())
                        .withEnvScore(company.getSustainlyticsEnvironmentalScore())
                        .withSocialScore(company.getSustainlyticsSocialScore())
                        .withGovScore(company.getSustainlyticsGovernenceScore())
                        .withESGCombinedScore(company.getSustainlyticsEsgScore())
                        .withControversyScore(0.00)
                        .withTotalEnvironmentalScore(company.getSustainalyticsTotalEnvironmentalScore())
                        .withTotalESGCombinedScore(company.getSustainalyticsTotalESGScore())
                        .withTotalESGScore(company.getSustainalyticsTotalESGScore())
                        .withTotalGovernanceScore(company.getSustainalyticsTotalGovernanceScore())
                        .withTotalSocialScore(company.getSustainalyticsTotalSocialScore())
                        .withInfluenceEnvironmentalScore(company.getInfluenceEnvironmentalScoreForSustainalytics())
                        .withInfluenceESGCombinedScore(company.getInfluenceESGScoreForSustainalytics())
                        .withInfluenceESGSCore(company.getInfluenceESGScoreForSustainalytics())
                        .withInfluenceGovernanceScore(company.getInfluenceGovernanceScoreForSustainalytics())
                        .withInfluenceSocialScore(company.getInfluenceSocialScoreForSustainalytics())
                        .withEnvironmentalFactors(null)
                        .withSocialFactors(null)
                        .withGovernanceFactors(null)
                        .withHoldingValue(company.getHoldingValue())
                        .withRefinitivNormalisedScore(company.getRefinitivNormalisedScore())
                        .withSustainalyticsNormalisedScore(company.getSustainlyticsNormalisedScore())
                        .withRefinitivESGCombinedScore(company.getEsgCombinedScore())
                        .withSustainalyticsESGCombinedScore(company.getSustainlyticsEsgScore())
                        .withOutLierScore(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ?  company.getOutlierScore() : 0)
                        .withSocialOutLierScore(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ?  company.getSocialOutlierScore() : 0)
                        .withEnvOutLierScore(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ?  company.getEnvOutlierScore() : 0)
                        .withGovOutLierScore(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ?  company.getGovOutlierScore() : 0)
                        .withIsOutLier(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE) ? company.getIsOutlier() : Boolean.FALSE)
                        .withIsinType(company.getIsinType())
                        .withNewsSentimentScore(applicationComponentSettingsMap.get(Constant.IS_NEWS_ENABLED).getValue().equals(Boolean.TRUE) ? company.getNewsSentimentScore() : null)
                        .withSector(company.getSector())
                        .build());
            } else if (PortfolioTypeEnum.FUND.getValue().equalsIgnoreCase(portfolio.getPortfolioIsinsType()) && portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.MSCI_INVESTIBLE_UNVIERSE_TYPE)){
                responseList.add(new CompanyDtoBuilder()
                        .withISIN(company.getIsin())
                        .withName(company.getName())
                        .withHoldingValue(company.getHoldingValue())
                        .withIsinType(company.getIsinType())
                        .withEsgMsciScore(company.getEsgMsciScore())
                        .withFdNr(PortfolioUtilities.normaliseDecimals(company.getFdNr()))
                        .withFdA(PortfolioUtilities.normaliseDecimals(company.getFdA()))
                        .withFdAA(PortfolioUtilities.normaliseDecimals(company.getFdAA()))
                        .withFdAAA(PortfolioUtilities.normaliseDecimals(company.getFdAAA()))
                        .withFdB(PortfolioUtilities.normaliseDecimals(company.getFdB()))
                        .withFdBB(PortfolioUtilities.normaliseDecimals(company.getFdBB()))
                        .withFdBBB(PortfolioUtilities.normaliseDecimals(company.getFdBBB()))
                        .withFdCCC(PortfolioUtilities.normaliseDecimals(company.getFdCCC()))
                        .withFundEsgScore(PortfolioUtilities.normaliseDecimals(company.getFundEsgScore()))
                        .withTotalFundEsgScore(PortfolioUtilities.normaliseDecimals(company.getTotalFundEsgScore()))
                        .withSustainalyticsESGCombinedScore(PortfolioUtilities.normaliseDecimals(company.getSustainlyticsEsgScore()))
                        .build());
            }else {
                log.error("Invalid data source.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
            }

        }
        return new CompaniesResponse(responseList);
    }

    private CompaniesResponse convertToCompaniesDto(List<CompanyESGScore> companies) {
        List<CompanyDto> responseList = new ArrayList<>();

        for (CompanyESGScore company : companies) {
                responseList.add(new CompanyDtoBuilder().withISIN(company.getIsin())
                        .withName(company.getInstrumentName())
                        .withESGScore(company.getEsgScore())
                        .withEnvScore(company.getEnvironmentPillarScore())
                        .withSocialScore(company.getSocialPillarScore())
                        .withGovScore(company.getGovernancePillarScore())
                        .withESGCombinedScore(company.getEsgCombinedScore())
                        .withControversyScore(company.getEsgControversiesScore())
                        .withTotalEnvironmentalScore(company.getEnvironmentPillarScore())
                        .withTotalESGCombinedScore(company.getEsgCombinedScore())
                        .withTotalESGScore(company.getEsgScore())
                        .withTotalGovernanceScore(company.getGovernancePillarScore())
                        .withTotalSocialScore(company.getSocialPillarScore())
                        .withEnvironmentalFactors(company.getEnvironmentalFactors())
                        .withSocialFactors(company.getSocialFactors())
                        .withGovernanceFactors(company.getGovernanceFactors())
                        .withRefinitivNormalisedScore(company.getRefinitivNormalizedScore())
                        .withSustainalyticsNormalisedScore(company.getSustainalyticsNormalisedScore())
                        .withRefinitivESGCombinedScore(company.getEsgCombinedScore())
                        .withSustainalyticsESGCombinedScore(company.getSustainalyticsTotalEsgScore())
                        .withSustainalyticsEnvScore(company.getSustainalyticsEnvironmentScore())
                        .withSustainalyticsSocialScore(company.getSustainalyticsSocialScore())
                        .withSustainalyticsGovScore(company.getSustainalyticsGovernanceScore())
                        .withOutLierScore(company.getOutlier())
                        .withIsinType(company.getIsinType())
                        .withEsgMsciScore(company.getEsgMsciScore())
                        .withFdNr(PortfolioUtilities.normaliseDecimals(company.getFdNr()))
                        .withFdA(PortfolioUtilities.normaliseDecimals(company.getFdA()))
                        .withFdAA(PortfolioUtilities.normaliseDecimals(company.getFdAA()))
                        .withFdAAA(PortfolioUtilities.normaliseDecimals(company.getFdAAA()))
                        .withFdB(PortfolioUtilities.normaliseDecimals(company.getFdB()))
                        .withFdBB(PortfolioUtilities.normaliseDecimals(company.getFdBB()))
                        .withFdBBB(PortfolioUtilities.normaliseDecimals(company.getFdBBB()))
                        .withFdCCC(PortfolioUtilities.normaliseDecimals(company.getFdCCC()))
                        .withFundEsgScore(PortfolioUtilities.normaliseDecimals(company.getFundEsgScore()))
                        .withTotalFundEsgScore(PortfolioUtilities.normaliseDecimals(company.getTotalFundEsgScore()))
                        .build());
            }

        return new CompaniesResponse(responseList);
    }

    @Override
    public List<Company> processUploadedPortfolioData(MultipartFile file, String fileName) throws FileValidationError, Exception {

        List<Company> calculatedCompanyDataList = new ArrayList<>();

        try {
            if (validateFileExtension(fileName)) {
                calculatedCompanyDataList = processUploadedData(file, fileName);
            }
        } catch (FileValidationError fileValidationError) {
            throw fileValidationError;
        } catch (ResponseStatusException responseStatusException) {
            throw responseStatusException;
        }catch (Exception exception) {
            throw new Exception("Error occurred while validating the file.");
        }
        return calculatedCompanyDataList;
    }

    public Boolean validateFileExtension(String fileName) {

        log.info("Start validation for file extension.");

        Boolean isValidFile = true;
        String fileExtension = Files.getFileExtension(fileName);
        if (!fileExtension.toUpperCase().equals(CSV_EXT)) {
            log.error("Invalid file type. Please upload .csv extension file.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid file type. Please upload .csv extension file.");
        }

        log.info("File validation for file extension successful.");
        return isValidFile;
    }

    @Override
    public Portfolio createPortfolio(String portfolioId, String portfolioName, String portfolioType, String investibleUniverseType, List<Company> companyDataList, String fileName) throws IOException {
        log.info("Creating portfolio.");
        Portfolio portfolio = new Portfolio();

        //Check portfolio ID, if empty add new and if present set the same.
        if (portfolioId.isEmpty())
            portfolio.setPortfolioId(PortfolioUtilities.randomPortfolioIdGenerator());
        else {
            portfolio.setPortfolioId(portfolioId);
        }
        //Check portfolio name, if empty give filename as portfolio name and if present set the same
        if (portfolioName.isEmpty()) {
            if (!checkIfNameDuplicate(FilenameUtils.removeExtension(fileName)))
                portfolio.setPortfolioName(FilenameUtils.removeExtension(fileName));
            else {
                portfolio.setPortfolioName(PortfolioUtilities.getCustomPortfolioName(Constant.PORTFOLIO_NAME, hazelcastInstance.getMap(PORTFOLIOS)));
            }
        } else {
            portfolio.setPortfolioName(portfolioName);
        }

        //Set portfolio type
        portfolio.setPortfolioType(portfolioType);

        //Check portfolio investible universe type and set accordingly
        if (investibleUniverseType.equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)) {
            portfolio.setInvestableUniverseType(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE);
        } else if (investibleUniverseType.equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE)) {
            portfolio.setInvestableUniverseType(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE);
        }

        //Set companies
        portfolio.setCompanies(companyDataList);

        //Set investible universe filter
        InvestableUniverseFilter filter = new InvestableUniverseFilter(0, 0, 0, 0, new ArrayList<>(), new ArrayList<>(), 0, 0, 0, 0, 0);
        portfolio.setInvestableUniverseFilter(filter);

        log.info("Portfolio created successfully. Portfolio : " + portfolio);
        return portfolio;
    }

    public List<Company> processUploadedData(MultipartFile file, String fileName) throws FileValidationError {
        log.info("Start processing the uploaded file.");

        List<CompanyModel> companies = null;
        List<Company> calculatedCompanyDataList = null;

        InputStream object = null;
        try {
            if (file == NULL && fileName != NULL) {
                MinioClient minioClient = new MinioClient(minioUrl, minioUser, minioPassword);
                object = minioClient.getObject(GetObjectArgs.builder()
                        .bucket(Constant.MINIO_BUCKET_NAME)
                        .object(fileName)
                        .build());
            } else {
                object = file.getInputStream();
            }
        } catch (Exception ex) {
            log.error("Error occurred while fetching file from minio, check for connection.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while fetching file from minio, Check for Connection.");
        }
        //Parse csv into bean
        try (Reader reader = new BufferedReader(new InputStreamReader(object))) {

            CsvToBean<CompanyModel> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CompanyModel.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            companies = csvToBean.parse();

            //Get valid company data from the uploaded data.
            List<Company> validCompanies = getValidCompanyData(companies);
            if (validCompanies != NULL && validCompanies.size() > 0) {
                //Perform calculations only if no faulty records - make a single record out of duplicate isin company's records.
                calculatedCompanyDataList = performCompanyDataCalculations(validCompanies);
            } else {
                log.error("No valid company data found.");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No valid company data found.");
            }
        } catch (FileValidationError fileValidationError) {
            throw fileValidationError;
        } catch (ResponseStatusException responseStatusException) {
            throw responseStatusException;
        }catch (Exception exception) {
            log.error("Uploaded file contains invalid or empty columns data.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Uploaded file contains invalid or empty columns data.");
        }

        log.info("Processed the uploaded file successfully.");
        return calculatedCompanyDataList;
    }

    public List<Company> getValidCompanyData(List<CompanyModel> companies) throws Exception, FileValidationError {
        log.info("Start validating company data from the uploaded file.");

        int companyCount = 0;
        List<Company> validCompanies = new ArrayList<>();
        Map<String, String> fileErrorsMap = new HashMap<>();

        if (companies.size() > 0) {
            for (CompanyModel companyModel : companies) {
                if ((companyModel.getIsin() != NULL && !companyModel.getIsin().isEmpty())) {
                    if (!(companyModel.getWt() == 0 && companyModel.getAmountInvested() == 0)) {
                        if(companyModel.getWt() < 0 || companyModel.getWt() > 100 || companyModel.getAmountInvested() < 0){
                            companyCount += 1;
                            log.error(RECORD + companyCount, " - Weightage / Amount Invested value is not in proper range. Data provided : " + companyModel.toString());
                            fileErrorsMap.put(RECORD + companyCount, " - Weightage / Amount Invested value is not in proper range. Data provided : " + companyModel.toString());
                        }else {
                            companyCount += 1;
                            log.info(RECORD + companyCount + " - Valid Company. Data provided : " + companyModel.toString());
                            validCompanies.add(new Company(companyModel.getIsin(), companyModel.getName(), companyModel.getWt(), companyModel.getAmountInvested()));
                        }
                    } else {
                        companyCount += 1;
                        log.error(RECORD + companyCount, " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : " + companyModel.toString());
                        fileErrorsMap.put(RECORD + companyCount, " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : " + companyModel.toString());
                    }
                } else {
                    companyCount += 1;
                    log.error(RECORD + companyCount, " - Company record must contain data for ISIN. Data provided : " + companyModel.toString());
                    fileErrorsMap.put(RECORD + companyCount, " - Company record must contain data for ISIN. Data provided : " + companyModel.toString());
                }
            }
        } else {
            log.error("Uploaded file contains invalid or empty company data.");
            throw new Exception("Uploaded file contains invalid or empty company data.");
        }

        //Throw error if fileErrorsMap contains even one entry
        if (fileErrorsMap.size() > 0) {
            log.error("Uploaded file contains incorrect company data records.");
            throw new FileValidationError(HttpStatus.BAD_REQUEST, "Uploaded file contains incorrect company data records.", fileErrorsMap);
        }

        //Check if all companies are valid companies
        if(validCompanies.size() != companies.size()){
            log.error("Uploaded file contains few incorrect company data records.");
            throw new FileValidationError(HttpStatus.BAD_REQUEST, "Uploaded file contains few incorrect company data records.", fileErrorsMap);
        }

        log.info("Validation of company data from the uploaded file successfull.");
        return validCompanies;
    }

    public List<Company> performCompanyDataCalculations(List<Company> companies) throws FileValidationError {
        log.info("Performing company data calculations.");

        double totalWeightage = 0;
        long sumofAmountInvested=0;
        Map<String, Company> calculatedCompanyMap = new HashMap<>();

        //Validate uploaded file data
        validateUploadedFileCompanies(companies);

        for (Company company : companies) {
            String isinKey = company.getIsin();
            if (calculatedCompanyMap.containsKey(isinKey)) {
                Company mapCompany = calculatedCompanyMap.get(isinKey);
                mapCompany.setWt(mapCompany.getWt() + company.getWt());
                mapCompany.setAmountInvested(mapCompany.getAmountInvested() + company.getAmountInvested());
                mapCompany.setHoldingValue(mapCompany.getHoldingValue() + company.getWt());
            } else {
                company.setHoldingValue(company.getWt());
                calculatedCompanyMap.put(isinKey, company);
            }
            //Check total weightage
            totalWeightage += company.getWt();
            sumofAmountInvested+=company.getAmountInvested(); 
        }

        //Get list of companies from map
        List<Company> calculatedCompanyDataList = new ArrayList<>();
        int totalNoOfPortfolioCompanies = companies.size(); //Total No of companies in the file
        int totalNoOfWeightagesPopulated = companies.stream().filter(company -> company.getWt() != 0).collect(Collectors.toList()).size();  //Total no of weights provided
        
        //Check if total weightage is zero , calculate the weightage from the amount invested.
       if(totalNoOfWeightagesPopulated<totalNoOfPortfolioCompanies)
       {
    	   totalWeightage=0.0;
           //Sum all amount invested and get the inidividual company weightage from the amount invested value for each company.
    	   for (String isin : calculatedCompanyMap.keySet()) {
    		   Company company=calculatedCompanyMap.get(isin);
    		   Double companyInvestedAmount=Double.parseDouble(String.valueOf(company.getAmountInvested()));
   	           Double totalAmountInvestedForPortfolio=Double.parseDouble(String.valueOf(sumofAmountInvested));
    		   Double weightageFromAmountInvested=(companyInvestedAmount/totalAmountInvestedForPortfolio)*100;
   	           company.setWt(PortfolioUtilities.normaliseDecimals(weightageFromAmountInvested));
   	           company.setHoldingValue(PortfolioUtilities.normaliseDecimals(weightageFromAmountInvested));
    		   calculatedCompanyMap.put(isin, company);
    		   totalWeightage+=weightageFromAmountInvested;
    	   }
    	   totalWeightage=PortfolioUtilities.normaliseDecimals(totalWeightage);
       }
       
        if ((totalWeightage == MAX_WEIGHTAGE) || (totalWeightage >= MIN_WEIGTHAGE && totalWeightage <= MAX_WEIGHTAGE)) {
            calculatedCompanyMap.entrySet().stream().forEach(company -> calculatedCompanyDataList.add(company.getValue()));
        } else {
            Map<String, String> fileErrorsMap = new HashMap<>();
            log.error("Total weightage of all the companies from the uploaded  portfolio must be equal to 100%.");
            throw new FileValidationError(HttpStatus.BAD_REQUEST, "Total weightage of all the companies from the uploaded  portfolio must be equal to 100%.", fileErrorsMap);
        }

        log.info("Portfolio data calculation successful.");
        return calculatedCompanyDataList;
    }

    private void validateUploadedFileCompanies(List<Company> companies){
        int totalNoOfPortfolioCompanies = companies.size(); //Total No of companies in the file
        int totalNoOfWeightagesPopulated = companies.stream().filter(company -> company.getWt() != 0).collect(Collectors.toList()).size();  //Total no of weights provided
        int totalNoOfAmountsPopulated = companies.stream().filter(company -> company.getAmountInvested() != 0).collect(Collectors.toList()).size(); //Total no of amounts provided

        if(totalNoOfWeightagesPopulated < totalNoOfPortfolioCompanies && totalNoOfAmountsPopulated < totalNoOfPortfolioCompanies){
            log.error("Multiple components are missing in the file. Kindly re-check Weightage / Amount Invested again and re-upload.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Multiple components are missing in the file. Kindly re-check Weightage / Amount Invested again and re-upload.");
        }
    }

    @Override
    public UpdatePortfolioResponse renamePortfolio(String portfolioId, String portfolioName) {
        if (!checkIfNameDuplicate(portfolioName)) {
            IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
            Portfolio portfolio = dataStore.get(portfolioId);
            portfolio.setPortfolioName(portfolioName);
            portfolio.setCreatedDate(new Date());
            return updatePortfolio(portfolio, Constant.UPDATE_NAME_PORTFOLIO);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Portfolio Name '" + portfolioName + "' already exists. Please try with different Portfolio Name.");
        }
    }

    @Override
    public ComparisonResponse getComparisonData(String portfolioId) {
        IMap<String, Portfolio> dataStore =
                hazelcastInstance.getMap(PORTFOLIOS);

        Portfolio portfolio = dataStore.get(portfolioId);
        ComparisonResponse response = null;
        if (Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE.equalsIgnoreCase(portfolio.getInvestableUniverseType())) {
            response = new ComparisonResponseBuilder()
                    .withSector(portfolio.getSector())
                    .withRegions(portfolio.getRegions())
                    .withESGSCore(portfolio.getEsgScore())
                    .withEnvSCore(portfolio.getEnvScore())
                    .withSocialScore(portfolio.getSocialScore())
                    .withGovScore(portfolio.getGovScore())
                    .withESGSCombinedCore(portfolio.getEsgCombinedScore())
                    .withCurrency(portfolio.getCurrency())
                    .withCountryAllocation(portfolio.getCountryAllocation())
                    .withCurrencyAllocation(portfolio.getCurrencyAllocation()).build();
        } else {
            response = new ComparisonResponseBuilder()
                    .withSector(portfolio.getSector())
                    .withRegions(portfolio.getRegions())
                    .withESGSCore(portfolio.getSustainalyticsCombinedScore())
                    .withEnvSCore(portfolio.getSustainalyticsEnvScore())
                    .withSocialScore(portfolio.getSustainalyticsSocialScore())
                    .withGovScore(portfolio.getSustainalyticsGovScore())
                    .withESGSCombinedCore(portfolio.getSustainalyticsCombinedScore())
                    .withCurrency(portfolio.getCurrency())
                    .withCountryAllocation(portfolio.getCountryAllocation())
                    .withCurrencyAllocation(portfolio.getCurrencyAllocation()).build();
        }
        return response;
    }

    @Override
    public MultipartFile getDefaultPortfolioCSV(String fileName) throws Exception {
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, Constant.UPLOAD_FILE_TYPE,
               // new FileInputStream(ResourceUtils.getFile(filePath + fileName))
                this.getClass().getClassLoader().getResourceAsStream(fileName)
        		);
        return multipartFile;
    }

    private Boolean checkIfNameDuplicate(String portfolioName) {

        Boolean isPortfolioNameDuplicate = false;
        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
        dataStore.loadAll(Boolean.TRUE);
        for (Map.Entry<String, Portfolio> entry : dataStore.entrySet()) {
            if (portfolioName.equalsIgnoreCase(dataStore.get(entry.getKey()).getPortfolioName())) {
                isPortfolioNameDuplicate = true;
                break;
            }
        }
        return isPortfolioNameDuplicate;
    }

    @Override
    public UpdatePortfolioResponse updateInvestibleUniverseTypeForPortfolio(String portfolioId, String investibleUniverseType) {
        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
        Portfolio portfolio = dataStore.get(portfolioId);
        portfolio.setInvestableUniverseType(investibleUniverseType.toUpperCase());
        return updatePortfolio(portfolio, Constant.UPDATE_INVESTIBLE_UNIVERSE_PORTFOLIO);
    }

    @Override
    public CompaniesResponse getOutLiersForPortFolio(String portfolioId) throws IOException {

        CompaniesResponse companiesResponse = new CompaniesResponse();
        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        if(applicationComponentSettingsMap.get(Constant.IS_OUTLIER_ENABLED).getValue().equals(Boolean.TRUE)) {

            IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
            Portfolio portfolio = dataStore.get(portfolioId);

            //List<Company> outLiers=portfolio.getCompanies().stream().filter(c->c.getIsOutlier().equals(Boolean.TRUE)).collect(Collectors.toList());
            //Every Company in the Portfolio is to be displayed as outlier and it will plotted
            List<Company> outLiers = portfolio.getCompanies();
            List<CompanyDto> responseList = new ArrayList<>();

            for (Company company : outLiers) {
                if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE)) {
                    responseList.add(new CompanyDtoBuilder().withISIN(company.getIsin())
                            .withName(company.getName()).withESGScore(company.getEsgScore())
                            .withEnvScore(company.getEnvironmentalScore())
                            .withSocialScore(company.getSocialScore())
                            .withGovScore(company.getGovernenceScore())
                            .withESGCombinedScore(company.getEsgCombinedScore())
                            .withControversyScore(company.getControversyScore())
                            .withTotalEnvironmentalScore(company.getTotalEnvironmentalScore())
                            .withTotalESGCombinedScore(company.getTotalESGCombinedScore())
                            .withTotalESGScore(company.getTotalESGScore())
                            .withTotalGovernanceScore(company.getTotalGovernanceScore())
                            .withTotalSocialScore(company.getTotalSocialScore())
                            .withInfluenceEnvironmentalScore(company.getInfluenceEnvironmentalScore())
                            .withInfluenceESGCombinedScore(company.getInfluenceESGCombinedScore())
                            .withInfluenceESGSCore(company.getInfluenceESGSCore())
                            .withInfluenceGovernanceScore(company.getInfluenceGovernanceScore())
                            .withInfluenceSocialScore(company.getInfluenceSocialScore())
                            .withEnvironmentalFactors(company.getEnvironmentalFactors())
                            .withSocialFactors(company.getSocialFactors())
                            .withGovernanceFactors(company.getGovernanceFactors())
                            .withHoldingValue(company.getHoldingValue())
                            .withNews(null)
                            .withRefinitivNormalisedScore(company.getRefinitivNormalisedScore())
                            .withSustainalyticsNormalisedScore(company.getSustainlyticsNormalisedScore())
                            .withRefinitivESGCombinedScore(company.getEsgCombinedScore())
                            .withSustainalyticsESGCombinedScore(company.getSustainlyticsEsgScore())
                            .withOutLierScore(company.getOutlierScore())
                            .withEnvOutLierScore(company.getEnvOutlierScore())
                            .withSocialOutLierScore(company.getSocialOutlierScore())
                            .withGovOutLierScore(company.getGovOutlierScore())
                            .withIsOutLier(company.getIsOutlier())
                            .build());
                } else if (portfolio.getInvestableUniverseType().equalsIgnoreCase(Constant.SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE)) {
                    responseList.add(new CompanyDtoBuilder().withISIN(company.getIsin())
                            .withName(company.getName()).withESGScore(company.getSustainlyticsEsgScore())
                            .withEnvScore(company.getSustainlyticsEnvironmentalScore())
                            .withSocialScore(company.getSustainlyticsSocialScore())
                            .withGovScore(company.getSustainlyticsGovernenceScore())
                            .withESGCombinedScore(company.getSustainlyticsEsgScore())
                            .withControversyScore(0.00)
                            .withTotalEnvironmentalScore(company.getSustainalyticsTotalEnvironmentalScore())
                            .withTotalESGCombinedScore(company.getSustainalyticsTotalESGScore())
                            .withTotalESGScore(company.getSustainalyticsTotalESGScore())
                            .withTotalGovernanceScore(company.getSustainalyticsTotalGovernanceScore())
                            .withTotalSocialScore(company.getSustainalyticsTotalSocialScore())
                            .withInfluenceEnvironmentalScore(company.getInfluenceEnvironmentalScoreForSustainalytics())
                            .withInfluenceESGCombinedScore(company.getInfluenceESGScoreForSustainalytics())
                            .withInfluenceESGSCore(company.getInfluenceESGScoreForSustainalytics())
                            .withInfluenceGovernanceScore(company.getInfluenceGovernanceScoreForSustainalytics())
                            .withInfluenceSocialScore(company.getInfluenceSocialScoreForSustainalytics())
                            .withEnvironmentalFactors(null)
                            .withSocialFactors(null)
                            .withGovernanceFactors(null)
                            .withHoldingValue(company.getHoldingValue())
                            .withNews(null)
                            .withRefinitivNormalisedScore(company.getRefinitivNormalisedScore())
                            .withSustainalyticsNormalisedScore(company.getSustainlyticsNormalisedScore())
                            .withRefinitivESGCombinedScore(company.getEsgCombinedScore())
                            .withSustainalyticsESGCombinedScore(company.getSustainlyticsEsgScore())
                            .withOutLierScore(company.getOutlierScore())
                            .withIsOutLier(company.getIsOutlier())
                            .withEnvOutLierScore(company.getEnvOutlierScore())
                            .withSocialOutLierScore(company.getSocialOutlierScore())
                            .withGovOutLierScore(company.getGovOutlierScore())
                            .build());
                } else {
                    log.error("Invalid data source.");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
                }
            }
            companiesResponse = new CompaniesResponse(responseList);
        }

        return companiesResponse;
    }

    @Override
    public ClonePortfolioResponse clonePortfolio(@RequestBody ClonePortfolioRequest clonePortfolioRequest) {
        ClonePortfolioResponse clonePortfolioResponse = new ClonePortfolioResponse();
        String portfolioName = clonePortfolioRequest.getPortfolioName();

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
        try {
            Portfolio portfolioToBeCloned = dataStore.get(clonePortfolioRequest.getPortfolioId());
            portfolioToBeCloned.setPortfolioId(PortfolioUtilities.randomPortfolioIdGenerator());
            if (!checkIfNameDuplicate(portfolioName)) {
                portfolioToBeCloned.setPortfolioName(portfolioName);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Portfolio Name '" + portfolioName + "' already exists. Please try with different Portfolio Name.");
            }

            dataStore.put(portfolioToBeCloned.getPortfolioId(), portfolioToBeCloned);
            clonePortfolioResponse.setStatus(HttpStatus.OK);
            clonePortfolioResponse.setMessage("Portfolio Cloned Successfully.");
        } catch (ResponseStatusException responseStatusException) {
            clonePortfolioResponse.setStatus(responseStatusException.getStatus());
            clonePortfolioResponse.setMessage(responseStatusException.getReason());
            log.error(Arrays.toString(responseStatusException.getStackTrace()).toString());
            log.error(responseStatusException.getMessage());
        } catch (Exception exception) {
            clonePortfolioResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            clonePortfolioResponse.setMessage("Exception occurred. Please Contact System Admin");
            log.error(Arrays.toString(exception.getStackTrace()));
            log.error(exception.getMessage());
        }
        return clonePortfolioResponse;
    }
    
	@Override
	public CompanyHistoricalResponse getCompanyHistoricalData(String isin, String investibleUniverseType) throws IOException {

        CompanyHistoricalResponse histRes = new CompanyHistoricalResponse();
        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        if(applicationComponentSettingsMap.get(Constant.IS_HISTORICAL_PERFORMANCE_ENABLED).getValue().equals(Boolean.TRUE)) {

            ResponseEntity<List<CompanyHistoricalData>> companyHistoricalData = dataLakeServiceProxy.getHistoricalData(isin, investibleUniverseType);

            List<String> year = companyHistoricalData.getBody().stream().map(e -> e.getDate().substring(0, 4))
                    .collect(Collectors.toList());
            List<Double> envScore = companyHistoricalData.getBody().stream().map(e -> PortfolioUtils.formatDouble(e.getEnvironmentalScore()))
                    .collect(Collectors.toList());
            List<Double> esgScore = companyHistoricalData.getBody().stream().map(esg -> PortfolioUtils.formatDouble(esg.getEsgScore()))
                    .collect(Collectors.toList());
            List<Double> govScore = companyHistoricalData.getBody().stream().map(g -> PortfolioUtils.formatDouble(g.getGovernanceScore()))
                    .collect(Collectors.toList());
            List<Double> esgCombinedScore = companyHistoricalData.getBody().stream().map(s -> PortfolioUtils.formatDouble(s.getEsgCombinedScore()))
                    .collect(Collectors.toList());
            List<Double> socialScore = companyHistoricalData.getBody().stream().map(social -> PortfolioUtils.formatDouble(social.getSocialScore()))
                    .collect(Collectors.toList());
            List<Double> sectorAvgScore = companyHistoricalData.getBody().stream().map(sectorAvg -> PortfolioUtils.formatDouble(sectorAvg.getSectorAvgScore()))
                    .collect(Collectors.toList());

            if(Constant.DS3.equalsIgnoreCase(investibleUniverseType))
                histRes = new CompanyHistoricalResponse(esgScore, null, null, null, null, null, year, getCompanyEsgMsciScore(companyHistoricalData.getBody()));
            else
                histRes = new CompanyHistoricalResponse(esgScore, envScore, socialScore, govScore, esgCombinedScore, sectorAvgScore, year, null);
        }

		return histRes;

	}

    @Override
    public CompanyCardAndESGDetailsDatalakeDto getCompanyCardDetails(String isin) {

        //Check if data present in cache, if not hit the datalake and fetch
        IMap<String, CompanyCardAndESGDetailsDatalakeDto> dataStore = hazelcastInstance.getMap("companyCards");
        CompanyCardAndESGDetailsDatalakeDto companyCardCache = dataStore.get(isin);

        if(companyCardCache == null){
            ResponseEntity<CompanyCardAndESGDetailsDatalakeDto> companyCardDetailsDatalake = dataLakeServiceProxy.getCompanyCardDetails(isin);
            if (!companyCardDetailsDatalake.getStatusCode().equals(HttpStatus.OK)) {
                log.error("Error occurred while retrieving company card and esg details from data lake.");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while retrieving company card and esg details from data lake.");
            }else if(companyCardDetailsDatalake.getBody() == null){
                companyCardCache = companyCardDetailsDatalake.getBody();
            }else {
                companyCardCache = companyCardDetailsDatalake.getBody();

                companyCardCache.setEsgScore(PortfolioUtils.formatDouble(companyCardCache.getEsgScore()));
                companyCardCache.setEsgCombinedScore(PortfolioUtils.formatDouble(companyCardCache.getEsgCombinedScore()));
                companyCardCache.setEsgControversiesScore(PortfolioUtils.formatDouble(companyCardCache.getEsgControversiesScore()));
                companyCardCache.setEnvironmentPillarScore(PortfolioUtils.formatDouble(companyCardCache.getEnvironmentPillarScore()));
                companyCardCache.setSocialPillarScore(PortfolioUtils.formatDouble(companyCardCache.getSocialPillarScore()));
                companyCardCache.setGovernancePillarScore(PortfolioUtils.formatDouble(companyCardCache.getGovernancePillarScore()));

                companyCardCache.setSustainalyticsTotalEsgScore(PortfolioUtils.formatDouble(companyCardCache.getSustainalyticsTotalEsgScore()));
                companyCardCache.setSustainalyticsEnvironmentScore(PortfolioUtils.formatDouble(companyCardCache.getSustainalyticsEnvironmentScore()));
                companyCardCache.setSustainalyticsSocialScore(PortfolioUtils.formatDouble(companyCardCache.getSustainalyticsSocialScore()));
                companyCardCache.setSustainalyticsGovernanceScore(PortfolioUtils.formatDouble(companyCardCache.getSustainalyticsGovernanceScore()));

                dataStore.put(isin, companyCardCache);
            }
        }
        return companyCardCache;
    }
    
	@Override
    public CompanyEmissionHistoricalGraph getCarbonEmissionHistData(String isin, String investibleUniverseType) throws IOException {

        CompanyEmissionHistoricalGraph companyEmissionHistoricalGraph = new CompanyEmissionHistoricalGraph();
        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        if(applicationComponentSettingsMap.get(Constant.IS_CO2_EMISSION_ENABLED).getValue().equals(Boolean.TRUE)) {
            ResponseEntity<CarbonEmissionHistoricalGraph> emissionHistoricalHistData = dataLakeServiceProxy.getCarbonEmissionHistData(isin, investibleUniverseType);
            companyEmissionHistoricalGraph = converToCompanyEmissionHistoricalGraph(emissionHistoricalHistData.getBody());
        }
        return companyEmissionHistoricalGraph;
    }

    public CompanyEmissionHistoricalGraph converToCompanyEmissionHistoricalGraph(CarbonEmissionHistoricalGraph responseGraph) {

        CompanyEmissionHistoricalGraph companyEmissionHistoricalGraph = new CompanyEmissionHistoricalGraph();
        List<CarbonEmissionResponse> carbonEmissionResponses = null;
        List<CompanyEmissionScopesHistoricalData> companyEmissionScopesHistoricalDataList = new ArrayList<>();
        List<CompanySectorAveragePerYear> companySectorAveragePerYears = null;

        if(responseGraph.getEmissionScopeResponse() != null) {
            //Scope1
            List<CarbonEmissionResponse> carbonEmissionResponse_scope1 = responseGraph.getEmissionScopeResponse().stream().map(temp -> {
                CompanyEmissionScopesHistoricalData companyEmissionScopesHistoricalDataObject = new CompanyEmissionScopesHistoricalData();
                CarbonEmissionResponse carbonEmissionResponse = new CarbonEmissionResponse();
                carbonEmissionResponse.setEmissionScore(temp.getCo2_equivalent_emissions_direct_scope_1());
                carbonEmissionResponse.setYear(temp.getYear());
                return carbonEmissionResponse;
            }).sorted(Comparator.comparing(CarbonEmissionResponse::getYear)).collect(Collectors.toList());

            //Scope2
            List<CarbonEmissionResponse> carbonEmissionResponse_scope2 = responseGraph.getEmissionScopeResponse().stream().map(temp -> {
                CompanyEmissionScopesHistoricalData companyEmissionScopesHistoricalDataObject = new CompanyEmissionScopesHistoricalData();
                CarbonEmissionResponse carbonEmissionResponse = new CarbonEmissionResponse();
                carbonEmissionResponse.setEmissionScore(temp.getCo2_equi_emissions_indirect_scope_2());
                carbonEmissionResponse.setYear(temp.getYear());
                return carbonEmissionResponse;
            }).sorted(Comparator.comparing(CarbonEmissionResponse::getYear)).collect(Collectors.toList());

            //Scope3
            List<CarbonEmissionResponse> carbonEmissionResponse_scope3 = responseGraph.getEmissionScopeResponse().stream().map(temp -> {
                CompanyEmissionScopesHistoricalData companyEmissionScopesHistoricalDataObject = new CompanyEmissionScopesHistoricalData();
                CarbonEmissionResponse carbonEmissionResponse = new CarbonEmissionResponse();
                carbonEmissionResponse.setEmissionScore(temp.getCo2_equi_emissions_indirect_scope_3());
                carbonEmissionResponse.setYear(temp.getYear());
                return carbonEmissionResponse;
            }).sorted(Comparator.comparing(CarbonEmissionResponse::getYear)).collect(Collectors.toList());

            companyEmissionScopesHistoricalDataList.add(new CompanyEmissionScopesHistoricalData("SCOPE_1", carbonEmissionResponse_scope1));
            companyEmissionScopesHistoricalDataList.add(new CompanyEmissionScopesHistoricalData("SCOPE_2", carbonEmissionResponse_scope2));
            companyEmissionScopesHistoricalDataList.add(new CompanyEmissionScopesHistoricalData("SCOPE_3", carbonEmissionResponse_scope3));
        }


        carbonEmissionResponses = responseGraph.getEmissionResponse().stream()
                .map(e -> {
                    e.setEmissionScore(PortfolioUtilities.normaliseDecimals(e.getEmissionScore()));
                    return e;
                }).sorted(Comparator.comparing(CarbonEmissionResponse::getYear)).collect(Collectors.toList());

        if(responseGraph.getSectorAveragePerYears() != null) {
            companySectorAveragePerYears = responseGraph.getSectorAveragePerYears().stream()
                            .map(e -> {
                                e.setAvgScore(PortfolioUtilities.normaliseDecimals(e.getAvgScore()));
                                return e;
                            }).sorted(Comparator.comparing(CompanySectorAveragePerYear::getYear)).collect(Collectors.toList());
        }

        //Set values in response object
        companyEmissionHistoricalGraph.setEmissionResponse(carbonEmissionResponses);
        companyEmissionHistoricalGraph.setSectorAveragePerYears(companySectorAveragePerYears);
        companyEmissionHistoricalGraph.setEmissionScopes(companyEmissionScopesHistoricalDataList.isEmpty() ? null : companyEmissionScopesHistoricalDataList);

        return companyEmissionHistoricalGraph;
    }

	@Override
	public List<CompanyDailyESGAndSocialPositionigData> getHistoricalDailyEsgScore(DailyESGScoreRequest dailyESGScoreRequest) throws IOException {

        List<CompanyDailyESGAndSocialPositionigData> companyDailyESGAndSocialPositionigDataList = new ArrayList<>();
        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        if(applicationComponentSettingsMap.get(Constant.IS_SOCIAL_SENTIMENT_ENABLED).getValue().equals(Boolean.TRUE)) {
            companyDailyESGAndSocialPositionigDataList = dataLakeServiceProxy.getCompanyDailyESGScore(dailyESGScoreRequest).getBody();
        }

		return companyDailyESGAndSocialPositionigDataList;
	}

    @Override
    public List<FundCompanyResponseDTO> getFundCompanies(String isin) {
        ResponseEntity<List<FundCompanyDTO>> fundCompaniesResponseEntity = dataLakeServiceProxy.getFundCompanies(isin);
        if (!fundCompaniesResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            log.error("Error occurred while retrieving company card and esg details from data lake.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while retrieving company card and esg details from data lake.");
        }

        List<FundCompanyDTO> fundCompanyDTOS = fundCompaniesResponseEntity.getBody();
        List<FundCompanyResponseDTO> fundCompanyResponseDTOS = fundCompanyDTOS.stream().map(f -> new FundCompanyResponseDTO(f.getIsin(), f.getCompanyName(), f.getPercentHolding())).collect(Collectors.toList());

        return fundCompanyResponseDTOS;
    }

    @Override
    public EsgRateDistributionResponseDTO getPortfolioEsgRatingDistribution(String portfolioId) {

        List<EsgRateDistributionsDTO> esgRateDistributionsDTOS = new ArrayList<>();
        EsgRateDistributionResponseDTO esgRateDistributionResponseDTO = new EsgRateDistributionResponseDTO();

        IMap<String, Portfolio> dataStore = hazelcastInstance.getMap(PORTFOLIOS);
        Portfolio portfolio = dataStore.get(portfolioId);

        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.AAA, portfolio.getFdAAA()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.AA, portfolio.getFdAA()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.A, portfolio.getFdA()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.BBB, portfolio.getFdBBB()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.BB, portfolio.getFdBB()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.B, portfolio.getFdB()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.CCC, portfolio.getFdCCC()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.NR, portfolio.getFdNr()));

        //Set reponse
        esgRateDistributionResponseDTO.setEsgRateDistributionsDTOList(esgRateDistributionsDTOS);
        esgRateDistributionResponseDTO.setLegends((int) Math.round(portfolio.getFdAA() + portfolio.getFdAAA()));
        esgRateDistributionResponseDTO.setLaggards((int) Math.round(portfolio.getFdB() + portfolio.getFdCCC()));

        return esgRateDistributionResponseDTO;
    }

    @Override
    public EsgRateDistributionResponseDTO getFundEsgRatingDistribution(String isin) {

        List<EsgRateDistributionsDTO> esgRateDistributionsDTOS = new ArrayList<>();
        EsgRateDistributionResponseDTO esgRateDistributionResponseDTO = new EsgRateDistributionResponseDTO();

        CompaniesResponse companiesResponse = getCompanies();
        CompanyDto companyDto = companiesResponse.getCompanies().stream().filter(c -> isin.equals(c.getIsin())).findFirst().get();


        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.AAA, companyDto.getFdAAA()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.AA, companyDto.getFdAA()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.A, companyDto.getFdA()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.BBB, companyDto.getFdBBB()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.BB, companyDto.getFdBB()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.B, companyDto.getFdB()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.CCC, companyDto.getFdCCC()));
        esgRateDistributionsDTOS.add(new EsgRateDistributionsDTO(Constant.NR, companyDto.getFdNr()));

        //Set reponse
        esgRateDistributionResponseDTO.setEsgRateDistributionsDTOList(esgRateDistributionsDTOS);
        esgRateDistributionResponseDTO.setLegends((int) Math.round(companyDto.getFdAA() + companyDto.getFdAAA()));
        esgRateDistributionResponseDTO.setLaggards((int) Math.round(companyDto.getFdB() + companyDto.getFdCCC()));

        return esgRateDistributionResponseDTO;
    }

    public List<String> getCompanyEsgMsciScore(List<CompanyHistoricalData> companyHistoricalDataList) {


        List<String> esgMsciScoresList = new ArrayList<>();

        for (CompanyHistoricalData companyHistoricalData : companyHistoricalDataList) {
            String esgMsciScore = "";
            Double fundEsgScore = companyHistoricalData.getEsgScore();
            if (0.0 <= fundEsgScore && 1.429 >= fundEsgScore) {
                esgMsciScore = "CCC";
            } else if (1.429 < fundEsgScore && 2.859 >= fundEsgScore) {
                esgMsciScore = "B";
            } else if (2.859 <= fundEsgScore && 4.286 >= fundEsgScore) {
                esgMsciScore = "BB";
            } else if (4.286 <= fundEsgScore && 5.714 >= fundEsgScore) {
                esgMsciScore = "BBB";
            } else if (5.714 <= fundEsgScore && 7.143 >= fundEsgScore) {
                esgMsciScore = "A";
            } else if (7.143 <= fundEsgScore && 8.571 >= fundEsgScore) {
                esgMsciScore = "AA";
            } else if (8.571 <= fundEsgScore && 10.0 >= fundEsgScore) {
                esgMsciScore = "AAA";
            } else {
                //throw error
            }

            //Put in the list
            esgMsciScoresList.add(esgMsciScore);
        }
        return esgMsciScoresList;
    }
}
    


