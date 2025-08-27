package org.synechron.portfolio.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.synechron.esg.model.Company;
import org.synechron.esg.model.CompanyCardAndESGDetailsDatalakeDto;
import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.dto.DailyESGScoreRequest;
import org.synechron.portfolio.error.FileValidationError;
import org.synechron.portfolio.request.ClonePortfolioRequest;
import org.synechron.portfolio.response.dto.*;
import org.synechron.portfolio.response.history.CompanyEmissionHistoricalGraph;
import org.synechron.portfolio.response.history.CompanyHistoricalResponse;

public interface PortfolioService {

    public PortfolioListResponse getAllPortfolios() throws FileValidationError, Exception;

    public CreatePortfolioResponse insertPortfolio(Portfolio portfolio) throws Exception;

    public UpdatePortfolioResponse updatePortfolio(Portfolio portfolio, String operation);

    public PortfolioDto getPortfolio(String key) throws IOException;

    public DeletePortfolioResponse deletePortfolioBy(String key);

    public void evictAll();

    public CompaniesResponse getCompanies(String portfolioId) throws IOException;

    public CompaniesResponse getCompanies();

    public CompanyDto getCompany(String portfolioId, String isin) throws IOException;

    public List<Company> processUploadedPortfolioData(MultipartFile file, String fileName) throws FileValidationError, Exception;

    public Portfolio createPortfolio(String portfolioId, String portfolioName, String portfolioType, String investibleUniverseType, List<Company> companyDataList, String fileName) throws IOException;

    public UpdatePortfolioResponse renamePortfolio(String portfolioId, String portfolioName);

    public ComparisonResponse getComparisonData(String portfolioId);

    public MultipartFile getDefaultPortfolioCSV(String fileName) throws Exception;

    public UpdatePortfolioResponse updateInvestibleUniverseTypeForPortfolio(String portfolioId, String investibleUniverseType);

    public CompaniesResponse getOutLiersForPortFolio(String portfolioId) throws IOException;

    public ClonePortfolioResponse clonePortfolio(@RequestBody ClonePortfolioRequest clonePortfolioRequest);

    public CompanyHistoricalResponse getCompanyHistoricalData(String isin, String investibleUniverseType) throws IOException;

    public CompanyCardAndESGDetailsDatalakeDto getCompanyCardDetails(String isin);

    public List<CompanyDailyESGAndSocialPositionigData> getHistoricalDailyEsgScore(DailyESGScoreRequest dailyESGScoreRequest) throws IOException;
    
    public CompanyEmissionHistoricalGraph getCarbonEmissionHistData(String isin, String investibleUniverseType) throws IOException;

    public List<FundCompanyResponseDTO> getFundCompanies(String isin);

    public EsgRateDistributionResponseDTO getPortfolioEsgRatingDistribution(String portfolioId);

    public EsgRateDistributionResponseDTO getFundEsgRatingDistribution(String isin) throws IOException;
}
