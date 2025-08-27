package org.synechron.portfolio.service;

import org.synechron.portfolio.request.UpdateInvestmentUniverseFilterRequest;
import org.synechron.portfolio.response.dto.InvestibleUniverseUIResponse;
import org.synechron.portfolio.response.dto.UpdateInvestibleUniverseResponse;

public interface InvestibleUniverseService {

    public InvestibleUniverseUIResponse getInvestibleUniverse(String portfolioId);

    public UpdateInvestibleUniverseResponse updateInvestibleFilter(UpdateInvestmentUniverseFilterRequest request, String portfolioId);
}
