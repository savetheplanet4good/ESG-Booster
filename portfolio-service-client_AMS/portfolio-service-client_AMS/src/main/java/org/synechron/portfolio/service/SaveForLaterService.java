package org.synechron.portfolio.service;

import org.synechron.portfolio.request.SaveForLaterRequest;
import org.synechron.portfolio.response.dto.SaveForLaterResponse;

public interface SaveForLaterService {

    public SaveForLaterResponse saveForLaterPortfolio(SaveForLaterRequest saveForLaterRequest);
}
