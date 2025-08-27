package org.synechron.portfolio.service;

import org.synechron.portfolio.request.ReplaceCompaniesRequest;
import org.synechron.portfolio.response.dto.ReplaceCompanyResponse;

public interface ReplaceCompaniesService {

    public ReplaceCompanyResponse replaceCompanies(ReplaceCompaniesRequest request);
}
