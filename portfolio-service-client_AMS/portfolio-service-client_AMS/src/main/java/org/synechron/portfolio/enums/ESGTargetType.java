package org.synechron.portfolio.enums;

/**
 * The enum Esg target type.
 */
public enum ESGTargetType {

    /**
     * Esg esg target type.
     */
    ESG("ESG"),
    /**
     * E esg target type.
     */
    E("ENVIRONMENTAL"),
    /**
     * S esg target type.
     */
    S("SOCIAL"),
    /**
     * G esg target type.
     */
    G("GOVERNANCE");

    private String esgTarget;

    ESGTargetType(String esgTarget) {
        this.esgTarget = esgTarget;
    }

    /**
     * Gets target value.
     *
     * @return the target value
     */
    public String getTargetValue() {
        return esgTarget;
    }
}

