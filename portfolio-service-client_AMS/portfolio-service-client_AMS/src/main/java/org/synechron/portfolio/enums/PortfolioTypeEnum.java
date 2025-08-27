package org.synechron.portfolio.enums;

/**
 * The enum Portfolio type enum.
 */
public enum PortfolioTypeEnum {

    /**
     * Equity portfolio type enum.
     */
    EQUITY("EQUITY"),
    /**
     * Fund portfolio type enum.
     */
    FUND("FUND"),
    /**
     * Equity fund portfolio type enum.
     */
    EQUITY_FUND("EQUITY_FUND");

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
