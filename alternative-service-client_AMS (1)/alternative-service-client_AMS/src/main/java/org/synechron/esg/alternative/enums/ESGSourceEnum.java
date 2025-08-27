package org.synechron.esg.alternative.enums;

/**
 * The enum Esg source enum.
 */
public enum ESGSourceEnum {

    /**
     * Refinitive esg source enum.
     */
    Refinitive("SYNECHRON_1"),
    /**
     * Sustainanalytics esg source enum.
     */
    Sustainanalytics("SYNECHRON_2"),
    /**
     * Refinitiv esg source enum.
     */
    Refinitiv("SYNECHRON_1"),
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
