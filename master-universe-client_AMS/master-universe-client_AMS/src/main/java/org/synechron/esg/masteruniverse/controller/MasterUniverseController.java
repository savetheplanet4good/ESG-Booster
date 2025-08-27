package org.synechron.esg.masteruniverse.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.synechron.esg.masteruniverse.request.UpdateInvestmentUniverseFilterRequest;
import org.synechron.esg.masteruniverse.response.InvestibleUniverseResponse;
import org.synechron.esg.masteruniverse.response.UpdateInvestibleUniverseResponse;
import org.synechron.esg.masteruniverse.service.InvestibleUniverseService;
import org.synechron.esg.model.Portfolio;

@RestController
public class MasterUniverseController extends BaseController{

    private static final Logger LOGGER = LogManager.getLogger(MasterUniverseController.class);

    @Autowired
    private InvestibleUniverseService investibleUniverseServiceImpl;

    @PostMapping("/fetch-investible-universe")
    public InvestibleUniverseResponse getInvestibleUniverse(@RequestBody Portfolio portfolio) {

        return investibleUniverseServiceImpl.getInvestibleUniverse(portfolio);
    }

    @GetMapping("/fetch-investible-universe/{portfolioId}")
    public InvestibleUniverseResponse getInvestibleUniverse(@PathVariable String portfolioId) {

        return investibleUniverseServiceImpl.getInvestibleUniverse(portfolioId);
    }

    @PostMapping("/update-investible-universe/{portfolioId}")
    public UpdateInvestibleUniverseResponse updateInvetibleUniverseFilter(@PathVariable String portfolioId, @RequestBody UpdateInvestmentUniverseFilterRequest request) {
        return investibleUniverseServiceImpl.updateInvestibleFilter(request, portfolioId);
    }
}
