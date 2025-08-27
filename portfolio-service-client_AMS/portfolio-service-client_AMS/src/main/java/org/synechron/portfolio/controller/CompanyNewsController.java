package org.synechron.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.portfolio.response.dto.HeatMapResponseDto;
import org.synechron.portfolio.response.dto.NewFeedsResponseDto;
import org.synechron.portfolio.service.CompanyNewsService;

@RestController
public class CompanyNewsController extends BaseController {

    @Autowired
    private CompanyNewsService companyNewsService;

    @GetMapping(value = "/company-heap-map/{isin}")
    public HeatMapResponseDto getCompanyHeatMap(@PathVariable String isin) {
        return companyNewsService.getCompanyHeatMap(isin);
    }

    @GetMapping(value = "/company-news-feeds/{isin}")
    public NewFeedsResponseDto getCompanyNewsFeeds(@PathVariable String isin) {
        return companyNewsService.getCompanyNewsFeeds(isin);
    }

}
