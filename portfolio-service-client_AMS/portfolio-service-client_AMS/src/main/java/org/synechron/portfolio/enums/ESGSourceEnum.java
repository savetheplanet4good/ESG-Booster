package org.synechron.portfolio.enums;

/**
 * The enum Esg source enum.
 */
public enum ESGSourceEnum {

    /**
     * Refinitiv esg source enum.
     */
    REFINITIV("SYNECHRON_1"),
    /**
     * Sustainalytics esg source enum.
     */
    SUSTAINALYTICS("SYNECHRON_2");

    private String esgSource;

    ESGSourceEnum(String esgSource) {
        this.esgSource = esgSource;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return esgSource;
    }
}
