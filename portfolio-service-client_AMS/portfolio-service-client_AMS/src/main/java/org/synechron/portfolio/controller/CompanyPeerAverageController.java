package org.synechron.portfolio.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.portfolio.response.dto.CompanyPeerAverageUIResponseDto;
import org.synechron.portfolio.service.CompanyPeerAverageService;

import java.io.IOException;

@RestController
public class CompanyPeerAverageController extends BaseController{

    @Autowired
    private CompanyPeerAverageService companyPeerComparisonService;

    @GetMapping({"/company-peer-comparison/{portfolioId}/{isin}", "/company-peer-comparison/{isin}"})
    public CompanyPeerAverageUIResponseDto getCompanyPeerAverageData(@PathVariable(required = false) String portfolioId, @PathVariable String isin) throws IOException {
        return companyPeerComparisonService.getPeerAverageData(portfolioId, isin);
    }
}
