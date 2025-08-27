package org.synechron.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.model.Company;
import org.synechron.esg.model.Portfolio;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.error.FileValidationError;
import org.synechron.portfolio.request.UploadPortfolioFromMinioDto;
import org.synechron.portfolio.response.dto.CreatePortfolioResponse;
import org.synechron.portfolio.service.PortfolioService;

import java.util.List;

@RestController
public class CreatePortfolioController extends BaseController {

    @Autowired
    PortfolioService portfolioService;

    @ResponseBody
    @PostMapping(value = "/upload-portfolio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CreatePortfolioResponse uploadPortfolio(@RequestPart("investibleUniverseType") String investibleUniverseType, @RequestPart(required = true) MultipartFile file) throws Exception {
        CreatePortfolioResponse response = new CreatePortfolioResponse();
        try {
            List<Company> calculatedCompanyDataList = portfolioService.processUploadedPortfolioData(file, file.getOriginalFilename());
            Portfolio portfolioToUpload = portfolioService.createPortfolio("", "", Constant.PORTFOLIO_TYPE_NEW, investibleUniverseType, calculatedCompanyDataList, file.getOriginalFilename());
            response = portfolioService.insertPortfolio(portfolioToUpload);
        } catch (FileValidationError fileValidationError) {
            response.setStatus(fileValidationError.getStatus());
            response.setMessage(fileValidationError.getMessage());
            response.setErrorMap(fileValidationError.getFileErrorsMap());
        } catch (ResponseStatusException responseStatusException) {
            response.setStatus(responseStatusException.getStatus());
            response.setMessage(responseStatusException.getReason());
        }
        return response;
    }

    @ResponseBody
    @PostMapping(value = "/minio/upload-portfolio")
    public CreatePortfolioResponse uploadPortfolioFromMinio(@RequestBody UploadPortfolioFromMinioDto uploadPortfolioFromMinioDto) throws Exception {
        CreatePortfolioResponse response = new CreatePortfolioResponse();
        try {
            List<Company> calculatedCompanyDataList = portfolioService.processUploadedPortfolioData(null, uploadPortfolioFromMinioDto.getFileName());
            Portfolio portfolioToUpload = portfolioService.createPortfolio("", "", Constant.PORTFOLIO_TYPE_NEW, uploadPortfolioFromMinioDto.getInvestibleUniverseType(), calculatedCompanyDataList, uploadPortfolioFromMinioDto.getFileName());
            response = portfolioService.insertPortfolio(portfolioToUpload);
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

    @PostMapping("/generate-portfolio")
    public CreatePortfolioResponse generatePortfolio(@RequestParam(required = false) String generateString) throws Exception {
        CreatePortfolioResponse response = new CreatePortfolioResponse();
        try {
            MultipartFile file = portfolioService.getDefaultPortfolioCSV(Constant.DEFAULT_PORTFOLIO_API_UPLOAD_FILE_NAME);
            List<Company> calculatedCompanyDataList = portfolioService.processUploadedPortfolioData(file, file.getOriginalFilename());
            Portfolio portfolioToUpload = portfolioService.createPortfolio("", "", Constant.PORTFOLIO_TYPE_DEFAULT, Constant.REFINITIV_INVESTIBLE_UNVIERSE_TYPE, calculatedCompanyDataList, file.getOriginalFilename());
            response = portfolioService.insertPortfolio(portfolioToUpload);
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
}
