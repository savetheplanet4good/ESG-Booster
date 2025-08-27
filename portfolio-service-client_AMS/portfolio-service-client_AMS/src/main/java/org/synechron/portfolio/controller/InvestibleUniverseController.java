package org.synechron.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.synechron.portfolio.request.UpdateInvestmentUniverseFilterRequest;
import org.synechron.portfolio.response.dto.InvestibleUniverseResponse;
import org.synechron.portfolio.response.dto.InvestibleUniverseUIResponse;
import org.synechron.portfolio.response.dto.UpdateInvestibleUniverseResponse;
import org.synechron.portfolio.service.AlternativesService;
import org.synechron.portfolio.service.InvestibleUniverseService;

@RestController
public class InvestibleUniverseController extends BaseController {

    @Autowired
    private InvestibleUniverseService investibleUniverseServiceImpl;

    @Autowired
    private AlternativesService alternativesService;

    @GetMapping("/investibleUniverse/{portfolioId}")
    public InvestibleUniverseUIResponse getInvestibleUniverse(@PathVariable String portfolioId) {
        return investibleUniverseServiceImpl.getInvestibleUniverse(portfolioId);
    }

   @PostMapping("/investibleUniverse/{portfolioId}")
    public UpdateInvestibleUniverseResponse updateInvetibleUniverseFilter(@PathVariable String portfolioId, @RequestBody UpdateInvestmentUniverseFilterRequest request) {
        alternativesService.removeFromCache(portfolioId);
        return investibleUniverseServiceImpl.updateInvestibleFilter(request, portfolioId);
    }
}
