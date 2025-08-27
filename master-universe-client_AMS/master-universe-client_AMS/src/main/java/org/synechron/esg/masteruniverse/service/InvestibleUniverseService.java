package org.synechron.esg.masteruniverse.service;


import org.synechron.esg.masteruniverse.request.UpdateInvestmentUniverseFilterRequest;
import org.synechron.esg.masteruniverse.response.InvestibleUniverseResponse;
import org.synechron.esg.masteruniverse.response.UpdateInvestibleUniverseResponse;
import org.synechron.esg.model.Portfolio;

public interface InvestibleUniverseService {


    public InvestibleUniverseResponse getInvestibleUniverse(Portfolio portfolio);

    public InvestibleUniverseResponse getInvestibleUniverse(String portfolioId);
    
    public UpdateInvestibleUniverseResponse updateInvestibleFilter(UpdateInvestmentUniverseFilterRequest request, String portfolioId);
}
