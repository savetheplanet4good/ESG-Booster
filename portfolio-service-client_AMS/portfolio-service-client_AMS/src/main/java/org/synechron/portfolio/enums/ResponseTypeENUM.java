package org.synechron.portfolio.enums;

public enum ResponseTypeENUM {

    LIST("LIST"),
    LINECHART("LINECHART");

    public String getResponseType() {
        return responseType;
    }

    private String responseType;

    ResponseTypeENUM(String responseType) {
        this.responseType = responseType;
    }
}
