package org.synechron.esg.alternative.service;

import org.synechron.esg.alternative.request.AlternativeRequest;
import org.synechron.esg.alternative.response.AlternativeResponse;

/**
 * The interface Alternative service.
 */
public interface AlternativeService {

    /**
     * Gets alternatives.
     *
     * @param alternativeRequest the alternative request
     * @return the alternatives
     */
    public AlternativeResponse getAlternatives(AlternativeRequest alternativeRequest);


}
