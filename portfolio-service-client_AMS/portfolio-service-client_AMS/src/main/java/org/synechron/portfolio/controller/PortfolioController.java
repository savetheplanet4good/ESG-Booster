package org.synechron.portfolio.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.model.ApplicationComponentSettings;
import org.synechron.esg.model.Company;
import org.synechron.esg.model.CompanyCardAndESGDetailsDatalakeDto;
import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.application_settings.service.ApplicationComponentSettingsService;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.dto.DailyESGScoreRequest;
import org.synechron.portfolio.enums.PortfolioTypeEnum;
import org.synechron.portfolio.enums.ResponseTypeENUM;
import org.synechron.portfolio.error.FileValidationError;
import org.synechron.portfolio.request.ClonePortfolioRequest;
import org.synechron.portfolio.request.UpdateInvestibleUniverseTypeDTO;
import org.synechron.portfolio.request.UpdatePortfolioFromMinioDto;
import org.synechron.portfolio.response.dto.*;
import org.synechron.portfolio.response.history.*;
import org.synechron.portfolio.service.CalculationService;
import org.synechron.portfolio.service.CompanyNewsService;
import org.synechron.portfolio.service.PortfolioService;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import org.synechron.portfolio.service.impl.PortfolioUtilities;

@RestController
public class PortfolioController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioController.class);

    @Autowired
    private PortfolioService portfolioService;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.user}")
    private String minioUser;

    @Value("${minio.password}")
    private String minioPassword;

    @Autowired
    private CalculationService calculationService;

    @Autowired
    private CompanyNewsService companyNewsService;

    @Autowired
    private ApplicationComponentSettingsService applicationComponentSettingsService;

    @GetMapping("/portfolios")
    public PortfolioListResponse getPortfolios() throws FileValidationError, Exception {
        logger.debug("getPortfolio Started");
        return portfolioService.getAllPortfolios();
    }

    @GetMapping("/portfolio/{portfolioId}")
    public PortfolioAndLastYearEsgDto getPortfolio(@PathVariable String portfolioId) throws IOException {

        LastYearESGScores lastYearESGScores = null;

        PortfolioDto portfolioDto = portfolioService.getPortfolio(portfolioId);
        HistoryResponse historyResponse = calculationService.getHistoricalData(portfolioId, ResponseTypeENUM.LINECHART.getResponseType());

        //Fetch portfolio esg last year data
        if(PortfolioTypeEnum.FUND.getValue().equalsIgnoreCase(portfolioDto.getPortfolioIsinsType())){
            lastYearESGScores = new LastYearESGScores(
                    ((LineChartHistoryResponse) historyResponse).getEsgScore().get(((LineChartHistoryResponse) historyResponse).getEsgScore().size() - 2),
                    ((LineChartHistoryResponse) historyResponse).getEsgCombinedScore() == null ? null : ((LineChartHistoryResponse) historyResponse).getEsgCombinedScore().get(((LineChartHistoryResponse) historyResponse).getEsgCombinedScore().size() - 2),
                    ((LineChartHistoryResponse) historyResponse).getEnvScore() == null ? null : ((LineChartHistoryResponse) historyResponse).getEnvScore().get(((LineChartHistoryResponse) historyResponse).getEnvScore().size() - 2),
                    ((LineChartHistoryResponse) historyResponse).getSocialScore() == null ? null : ((LineChartHistoryResponse) historyResponse).getSocialScore().get(((LineChartHistoryResponse) historyResponse).getSocialScore().size() - 2),
                    ((LineChartHistoryResponse) historyResponse).getGovScore() == null ? null : ((LineChartHistoryResponse) historyResponse).getGovScore().get(((LineChartHistoryResponse) historyResponse).getGovScore().size() - 2)
            );
        }else {
            lastYearESGScores = new LastYearESGScores(
                    ((LineChartHistoryResponse) historyResponse).getEsgScore().get(((LineChartHistoryResponse) historyResponse).getEsgScore().size() - 2),
                    ((LineChartHistoryResponse) historyResponse).getEsgCombinedScore().get(((LineChartHistoryResponse) historyResponse).getEsgCombinedScore().size() - 2),
                    ((LineChartHistoryResponse) historyResponse).getEnvScore().get(((LineChartHistoryResponse) historyResponse).getEnvScore().size() - 2),
                    ((LineChartHistoryResponse) historyResponse).getSocialScore().get(((LineChartHistoryResponse) historyResponse).getSocialScore().size() - 2),
                    ((LineChartHistoryResponse) historyResponse).getGovScore().get(((LineChartHistoryResponse) historyResponse).getGovScore().size() - 2)
            );
        }

        PortfolioAndLastYearEsgDto portfolioAndLastYearEsgDto = new PortfolioAndLastYearEsgDto();

        //Set data in dto object
        portfolioAndLastYearEsgDto.setPortfolioId(portfolioDto.getPortfolioId());
        portfolioAndLastYearEsgDto.setPortfolioName(portfolioDto.getPortfolioName());
        portfolioAndLastYearEsgDto.setPortfolioType(portfolioDto.getPortfolioType());
        portfolioAndLastYearEsgDto.setInvestableUniverseType(portfolioDto.getInvestableUniverseType());
        portfolioAndLastYearEsgDto.setEsgCombinedScore(portfolioDto.getEsgCombinedScore());
        portfolioAndLastYearEsgDto.setEsgScore(portfolioDto.getEsgScore());
        portfolioAndLastYearEsgDto.setEnvScore(portfolioDto.getEnvScore());
        portfolioAndLastYearEsgDto.setSocialScore(portfolioDto.getSocialScore());
        portfolioAndLastYearEsgDto.setGovScore(portfolioDto.getGovScore());
        portfolioAndLastYearEsgDto.setEsgMsciScore(portfolioDto.getEsgMsciScore());
        portfolioAndLastYearEsgDto.setEnvironmentalFactors(portfolioDto.getEnvironmentalFactors());
        portfolioAndLastYearEsgDto.setSocialFactors(portfolioDto.getSocialFactors());
        portfolioAndLastYearEsgDto.setGovernanceFactors(portfolioDto.getGovernanceFactors());
        portfolioAndLastYearEsgDto.setCompanyCount(portfolioDto.getCompanyCount());
        portfolioAndLastYearEsgDto.setOutLierCount(portfolioDto.getOutLierCount());
        portfolioAndLastYearEsgDto.setFund(portfolioDto.getFund());
        portfolioAndLastYearEsgDto.setEquity(portfolioDto.getEquity());
        portfolioAndLastYearEsgDto.setCalculationType(portfolioDto.getCalculationType());
        portfolioAndLastYearEsgDto.setPortfolioIsinsType(portfolioDto.getPortfolioIsinsType());

        portfolioAndLastYearEsgDto.setLastYearESGScores(lastYearESGScores);

        return portfolioAndLastYearEsgDto;
    }

    @ResponseBody
    @PutMapping(value = "/portfolio", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CreatePortfolioResponse updatePortfolio(@RequestPart("portfolioId") String portfolioId, @RequestPart(required = true) MultipartFile file) throws Exception {
        CreatePortfolioResponse response = new CreatePortfolioResponse();
        try {
            List<Company> calculatedCompanyDataList = portfolioService.processUploadedPortfolioData(file, file.getOriginalFilename());
            PortfolioDto portfolioDtoToUpdate = portfolioService.getPortfolio(portfolioId);    //Backup the portfolio
            portfolioService.deletePortfolioBy(portfolioId);       //To ensure all values are flushed
            Portfolio portfolio = portfolioService.createPortfolio(portfolioDtoToUpdate.getPortfolioId(), portfolioDtoToUpdate.getPortfolioName(), portfolioDtoToUpdate.getPortfolioType(), portfolioDtoToUpdate.getInvestableUniverseType(), calculatedCompanyDataList, file.getOriginalFilename());
            response = portfolioService.insertPortfolio(portfolio);
        } catch (FileValidationError fileValidationError) {
            response.setStatus(fileValidationError.getStatus());
            response.setMessage(fileValidationError.getMessage());
            response.setErrorMap(fileValidationError.getFileErrorsMap());
        } catch (ResponseStatusException responseStatusException) {
            response.setStatus(responseStatusException.getStatus());
            response.setMessage(responseStatusException.getMessage());
        }
        return response;
    }

    @ResponseBody
    @PutMapping(value = "/minio/portfolio")
    public CreatePortfolioResponse updatePortfolioFromMinio(@RequestBody UpdatePortfolioFromMinioDto updatePortfolioMinioDto) throws Exception {
        CreatePortfolioResponse response = new CreatePortfolioResponse();
        try {
            List<Company> calculatedCompanyDataList = portfolioService.processUploadedPortfolioData(null, updatePortfolioMinioDto.getFileName());
            PortfolioDto portfolioDtoToUpdate = portfolioService.getPortfolio(updatePortfolioMinioDto.getPortfolioId());    //Backup portfolio
            portfolioService.deletePortfolioBy(updatePortfolioMinioDto.getPortfolioId());   //To ensure all values are flushed
            Portfolio portfolio = portfolioService.createPortfolio(portfolioDtoToUpdate.getPortfolioId(), portfolioDtoToUpdate.getPortfolioName(), portfolioDtoToUpdate.getPortfolioType(), portfolioDtoToUpdate.getInvestableUniverseType(), calculatedCompanyDataList, updatePortfolioMinioDto.getFileName());
            response = portfolioService.insertPortfolio(portfolio);
        } catch (FileValidationError fileValidationError) {
            response.setStatus(fileValidationError.getStatus());
            response.setMessage(fileValidationError.getMessage());
            response.setErrorMap(fileValidationError.getFileErrorsMap());
        } catch (ResponseStatusException responseStatusException) {
            response.setStatus(responseStatusException.getStatus());
            response.setMessage(responseStatusException.getMessage());
        }
        return response;
    }

    @DeleteMapping("/portfolio/{portfolioId}")
    public DeletePortfolioResponse deletePortfolio(@PathVariable String portfolioId) {
        return portfolioService.deletePortfolioBy(portfolioId);
    }

    @GetMapping("/companies/{portfolioId}")
    public CompaniesResponse getCompanies(@PathVariable(required = false) String portfolioId) throws IOException {
        return portfolioService.getCompanies(portfolioId);
    }

    @GetMapping("/companies/")
    public CompaniesResponse getAllCompanies() {
        return portfolioService.getCompanies();
    }

    @GetMapping("/companies/latestnews/{portfolioId}")
    public CompaniesResponse getCompaniesWithLatestNews(@PathVariable String portfolioId) throws IOException {
        return portfolioService.getCompanies(portfolioId);
    }

   @GetMapping("/company/{portfolioId}/{isin}")
    public CompanyDto getCompany(@PathVariable String portfolioId, @PathVariable String isin) throws IOException {

        return portfolioService.getCompany(portfolioId, isin);
    }

    @GetMapping("/evictAll")
    public void evict() {
        portfolioService.evictAll();
    }

    @GetMapping("/files")
    public List<FileResponse> getFiles() {
        List<FileResponse> fileNames = new ArrayList<>();
        MinioClient minioClient = new MinioClient(minioUrl, minioUser, minioPassword);

        try {

            Iterable<Result<Item>> results = minioClient.listObjects(Constant.MINIO_BUCKET_NAME);
            results.forEach(itemResult -> {
                try {
                    fileNames.add(new FileResponse(itemResult.get().objectName(),itemResult.get().size(),itemResult.get().lastModified()));
                   } catch (Exception ex) {
                    logger.error("Exception Message  : " + ex.getMessage());
                }

            });

        } catch (Exception ex) {
            logger.error("Exception Message  : " + ex.getMessage());
        }

        return fileNames;
    }

    @PutMapping(value = "/rename-portfolio")
    public UpdatePortfolioResponse renamePortfolio(@RequestBody RenamePortfolioDto renamePortfolioDTO) {
        UpdatePortfolioResponse response = new UpdatePortfolioResponse();
        try {
            if (!renamePortfolioDTO.getPortfolioId().isEmpty() && !renamePortfolioDTO.getPortfolioName().isEmpty()) {
                response = portfolioService.renamePortfolio(renamePortfolioDTO.getPortfolioId(), renamePortfolioDTO.getPortfolioName());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empty request.");
            }
        } catch (ResponseStatusException responseStatusException) {
            response.setStatus(responseStatusException.getStatus());
            response.setMessage(responseStatusException.getReason());
        }
        return response;
    }


    @GetMapping(value = "/comparison/{portfolioId}")
    public ComparisonResponse getComparisonData(@PathVariable String portfolioId) {

        return portfolioService.getComparisonData(portfolioId);
    }

    @PutMapping(value = "/investableUniverseType-portfolio")
    public UpdatePortfolioResponse updateInvestibleUniverseType(@RequestBody UpdateInvestibleUniverseTypeDTO updateInvestibleUniverseTypeDTO) {
        UpdatePortfolioResponse response = new UpdatePortfolioResponse();
        try {
            if (!updateInvestibleUniverseTypeDTO.getPortfolioId().isEmpty() && !updateInvestibleUniverseTypeDTO.getInvestibleUniverseType().isEmpty()) {
                response = portfolioService.updateInvestibleUniverseTypeForPortfolio(updateInvestibleUniverseTypeDTO.getPortfolioId(), updateInvestibleUniverseTypeDTO.getInvestibleUniverseType());
            }

        } catch (ResponseStatusException responseStatusException) {
            response.setStatus(responseStatusException.getStatus());
            response.setMessage(responseStatusException.getReason());
        }
        return response;
    }

    @GetMapping("/outliers/{portfolioId}")
    public CompaniesResponse getOutLiersForPortFolio(@PathVariable String portfolioId) throws IOException {
        return portfolioService.getOutLiersForPortFolio(portfolioId);
    }

    @PostMapping(value = "/clone-portfolio")
    public ClonePortfolioResponse clonePortfolio(@RequestBody ClonePortfolioRequest clonePortfolioRequest) {
        return portfolioService.clonePortfolio(clonePortfolioRequest);
    }

    @GetMapping({"/historicalData/{portfolioId}/{responseType}","/historicalData/{portfolioId}"})
    public HistoryResponse getHistoricalData(@PathVariable String portfolioId, @PathVariable(required = false) String responseType) throws IOException {
        if (StringUtils.isEmpty(responseType))
            responseType = ResponseTypeENUM.LIST.getResponseType();

        return calculationService.getHistoricalData(portfolioId,responseType);
    }
    
	@GetMapping({"/companyHistoricalData/{isin}/{investibleUniverseType}","/companyHistoricalData/{isin}"})
	public CompanyHistoricalResponse getCompanyHistoricalData(@PathVariable("isin") String isin,
                                                              @PathVariable(required = false) String investibleUniverseType) throws IOException {

        investibleUniverseType = StringUtils.isEmpty(investibleUniverseType) ? Constant.DS1 : investibleUniverseType;
		return portfolioService.getCompanyHistoricalData(isin, investibleUniverseType);
	}

    @GetMapping({"/company-card-details/{isin}","/company-card-details/{isin}/{investibleUniverseType}"})
    public CompanyCardEsgLastYrEsgDto getCompanyCardDetails(@PathVariable("isin") String isin,@PathVariable(required = false) String investibleUniverseType) throws IOException {

        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        String dataSource = Constant.DS1;
        CompanyCardAndESGDetailsDatalakeDto response = portfolioService.getCompanyCardDetails(isin);
        if (!StringUtils.isEmpty(investibleUniverseType))
            dataSource = investibleUniverseType;

        //Fetching last year ESG scores
        CompanyHistoricalResponse companyHistoricalResponse = portfolioService.getCompanyHistoricalData(isin, investibleUniverseType);
        LastYearESGScores lastYearESGScores = new LastYearESGScores();
        if(companyHistoricalResponse != null){
            lastYearESGScores = new LastYearESGScores(
                    companyHistoricalResponse.getEsgScore().get(companyHistoricalResponse.getEsgScore().size()-2),
                    companyHistoricalResponse.getEsgCombinedScore().get(companyHistoricalResponse.getEsgCombinedScore().size()-2),
                    companyHistoricalResponse.getEnvScore().get(companyHistoricalResponse.getEnvScore().size()-2),
                    companyHistoricalResponse.getSocialScore().get(companyHistoricalResponse.getSocialScore().size()-2),
                    companyHistoricalResponse.getGovScore().get(companyHistoricalResponse.getGovScore().size()-2));
        }

        //Fetching news sentiment score
        Map<String, Double> newsSentimentScoreMap = companyNewsService.getNewsSentimentScore(isin);

        //Populate response dto
        CompanyCardEsgLastYrEsgDto companyCardEsgLastYrEsgDto = new CompanyCardEsgLastYrEsgDto();

        companyCardEsgLastYrEsgDto.setIsin(response.getIsin());
        companyCardEsgLastYrEsgDto.setCompanyName(response.getCompanyName());
        companyCardEsgLastYrEsgDto.setGeneralName(response.getGeneralName());
        companyCardEsgLastYrEsgDto.setWikiLink(response.getWikiLink());
        companyCardEsgLastYrEsgDto.setIndustry(response.getIndustry());
        companyCardEsgLastYrEsgDto.setLeadership(response.getLeadership());
        companyCardEsgLastYrEsgDto.setRevenue(response.getRevenue());
        companyCardEsgLastYrEsgDto.setWebsite(response.getWebsite());
        companyCardEsgLastYrEsgDto.setDescription(response.getDescription());
        companyCardEsgLastYrEsgDto.setLogoUrl(response.getLogoUrl());
        companyCardEsgLastYrEsgDto.setNewsSentimentScore(applicationComponentSettingsMap.get(Constant.IS_NEWS_ENABLED).getValue().equals(Boolean.TRUE) ? PortfolioUtilities.normaliseDecimals(newsSentimentScoreMap.get(isin)) : null);

        if (Constant.DS1.equalsIgnoreCase(dataSource)) {
            companyCardEsgLastYrEsgDto.setEsgScore(response.getEsgScore());
            companyCardEsgLastYrEsgDto.setEsgCombinedScore(response.getEsgCombinedScore());
            companyCardEsgLastYrEsgDto.setEsgControversiesScore(response.getEsgControversiesScore());
            companyCardEsgLastYrEsgDto.setEnvScore(response.getEnvironmentPillarScore());
            companyCardEsgLastYrEsgDto.setSocialScore(response.getSocialPillarScore());
            companyCardEsgLastYrEsgDto.setGovScore(response.getGovernancePillarScore());
        }else{
            companyCardEsgLastYrEsgDto.setEsgScore(response.getSustainalyticsTotalEsgScore());
            companyCardEsgLastYrEsgDto.setEsgCombinedScore(response.getSustainalyticsTotalEsgScore());
            companyCardEsgLastYrEsgDto.setEsgControversiesScore(0.0);
            companyCardEsgLastYrEsgDto.setEnvScore(response.getSustainalyticsEnvironmentScore());
            companyCardEsgLastYrEsgDto.setSocialScore(response.getSustainalyticsSocialScore());
            companyCardEsgLastYrEsgDto.setGovScore(response.getSustainalyticsGovernanceScore());
        }

        companyCardEsgLastYrEsgDto.setLastYearESGScores(lastYearESGScores);
        return companyCardEsgLastYrEsgDto;
    }
    
	@GetMapping({"/carbonEmissionHistData/{isin}/{investibleUniverseType}", "/carbonEmissionHistData/{isin}"})
	public CompanyEmissionHistoricalGraph getCarbonEmissionHistData(@PathVariable("isin") String isin, @PathVariable(required = false) String investibleUniverseType) throws IOException {

        investibleUniverseType = StringUtils.isEmpty(investibleUniverseType) ? Constant.DS1 : investibleUniverseType;
		return portfolioService.getCarbonEmissionHistData(isin, investibleUniverseType);
	}

	@PostMapping("/getDailyESGScore")
	public List<CompanyDailyESGAndSocialPositionigData> getDailyESGScoreAndSocialPositionig(@RequestBody DailyESGScoreRequest dailyESGScoreRequest) throws IOException {
		return portfolioService.getHistoricalDailyEsgScore(dailyESGScoreRequest);
	}

    @GetMapping("/fund-companies/{isin}")
    public List<FundCompanyResponseDTO> getFundCompanies(@PathVariable(required = true) String isin) throws IOException {
        return portfolioService.getFundCompanies(isin);
    }

    @GetMapping("/portfolio-rating-distribution/{portfolioId}")
    public EsgRateDistributionResponseDTO getPortfolioEsgRatingDistribution(@PathVariable(required = true) String portfolioId) {
        return portfolioService.getPortfolioEsgRatingDistribution(portfolioId);
    }

    @GetMapping("/fund-rating-distribution/{isin}")
    public EsgRateDistributionResponseDTO getFundEsgRatingDistribution(@PathVariable(required = true) String isin) throws IOException {
        return portfolioService.getFundEsgRatingDistribution(isin);
    }
}
