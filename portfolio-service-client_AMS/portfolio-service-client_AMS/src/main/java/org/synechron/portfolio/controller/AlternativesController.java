package org.synechron.portfolio.controller;

import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.synechron.esg.model.AlternativesObject;
import org.synechron.portfolio.request.GetAlternativeRequest;
import org.synechron.esg.model.AlternativeResponse;
import org.synechron.portfolio.service.AlternativesService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AlternativesController extends BaseController {

    @Autowired
    private AlternativesService alternativesService;

    @GetMapping("/alternatives/{portfolioId}/{isin}")
    public AlternativeResponse getAlternatives(@PathVariable String portfolioId, @PathVariable String isin) {

        AlternativeResponse response = null;
        List<String> isinList = new ArrayList<>();
        isinList.add(isin);
        return alternativesService.getAlternatives(isin, portfolioId, "ESG");
    }

    @GetMapping("/refresh/{portfolioId}")
    public void clearAlternativeCache(@PathVariable String portfolioId) {
        alternativesService.removeFromCache(portfolioId);
    }

    @PostMapping("/alternatives")
    public AlternativeResponse getAlternative(@RequestBody GetAlternativeRequest request) throws Exception {
        AlternativeResponse response = null;
        return alternativesService.getAlternative(request.getIsin(), request.getPortfolioId(), request.getEsgTargetType());
    }

    @GetMapping("/cache-alternatives/{portfolioId}")
    public void cacheAlternatives(@PathVariable String portfolioId) throws Exception{
        alternativesService.cacheAlternatives(portfolioId);
    }

    @PostMapping("/evict-cache")
    public void evictCache() throws Exception{
        alternativesService.evictCache();
    }

    @PostMapping("/load-cached-responses")
    public void loadCachedResponses() throws Exception{
        alternativesService.loadCachedResponses();
    }

}
