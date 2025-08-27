package org.synechron.portfolio.enums;

/**
 * The enum Calculation type.
 */
public enum CalculationType {

    /**
     * Fund calculation type.
     */
    FUND("FUND"),
    /**
     * Equity calculation type.
     */
    EQUITY("EQUITY"),
    /**
     * Fundandequity calculation type.
     */
    FUNDANDEQUITY("FUNDANDEQUITY");

    private String calculationType;

    CalculationType(String calculationType) {
        this.calculationType = calculationType;
    }

    /**
     * Gets target value.
     *
     * @return the target value
     */
    public String getTargetValue() {
        return calculationType;
    }
}
