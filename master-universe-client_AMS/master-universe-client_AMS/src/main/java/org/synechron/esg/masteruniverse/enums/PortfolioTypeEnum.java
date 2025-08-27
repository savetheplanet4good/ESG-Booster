package org.synechron.esg.masteruniverse.enums;

public enum PortfolioTypeEnum {

    Equity("EQUITY"), Fund("FUND"), Equity_Fund("EQUITY_FUND");

    private String portfolioType;

    PortfolioTypeEnum(String portfolioType) {
        this.portfolioType = portfolioType;
    }

    public String getValue() {
        return portfolioType;
    }

}
