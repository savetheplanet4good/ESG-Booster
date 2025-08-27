package org.synechron.esg.alternative.service;

/**
 * The interface Alternative factory.
 */
public interface AlternativeFactory {

    /**
     * Gets alternative service.
     *
     * @param alternativeServiceKey the alternative service key
     * @return the alternative service
     */
    public Alternative getAlternativeService(String alternativeServiceKey);
}
