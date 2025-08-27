package org.synechron.portfolio.enums;

/**
 * The enum Alternative type.
 */
public enum AlternativeType {

    /**
     * Investsuit alternative type.
     */
    INVESTSUIT("INVESTSUIT");

    /**
     * Gets alternative type.
     *
     * @return the alternative type
     */
    public String getAlternativeType() {
        return alternativeType;
    }

    private String alternativeType;

    AlternativeType(String alternativeType) {
        this.alternativeType = alternativeType;
    }
}
