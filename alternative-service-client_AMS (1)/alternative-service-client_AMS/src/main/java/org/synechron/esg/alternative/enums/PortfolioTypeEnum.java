package org.synechron.esg.alternative.enums;

/**
 * The enum Portfolio type enum.
 */
public enum PortfolioTypeEnum {

    /**
     * Equity portfolio type enum.
     */
    Equity("EQUITY"),
    /**
     * Fund portfolio type enum.
     */
    Fund("FUND"),
    /**
     * Equity fund portfolio type enum.
     */
    Equity_Fund("EQUITY_FUND");

    private String portfolioType;

    PortfolioTypeEnum(String portfolioType) {
        this.portfolioType = portfolioType;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return portfolioType;
    }

}
