package org.synechron.portfolio.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.response.dto.ForecastResponse;
import org.synechron.portfolio.service.ForecastService;

import java.io.IOException;

@RestController
public class ForecastController extends BaseController {

    @Autowired
    private ForecastService forecastService;


    @GetMapping({"/forecast/{isin}/{investibleUniverseType}","/forecast/{isin}"})
    public ForecastResponse getForecast(@PathVariable("isin") String isin, @PathVariable(required = false) String investibleUniverseType) throws IOException {

        ForecastResponse response = new ForecastResponse();
        investibleUniverseType = StringUtils.isEmpty(investibleUniverseType) ? Constant.DS1 : investibleUniverseType;

        response = forecastService.getForecast(isin, investibleUniverseType);

        return response;
    }

    @GetMapping("/forecast/featureImportance")
    public void getFeatureImportance() {
        forecastService.getFeatureImportance();
    }
}
