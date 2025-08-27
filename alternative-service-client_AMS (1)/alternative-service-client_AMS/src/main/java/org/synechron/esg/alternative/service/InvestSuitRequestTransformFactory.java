package org.synechron.esg.alternative.service;

/**
 * The interface Invest suit request transform factory.
 */
public interface InvestSuitRequestTransformFactory {


    /**
     * Gets alternative transform service.
     *
     * @param investibleUniverseType the investible universe type
     * @return the alternative transform service
     */
    public AlternativeRequestTransformService getAlternativeTransformServiceImpl(String investibleUniverseType);

}
