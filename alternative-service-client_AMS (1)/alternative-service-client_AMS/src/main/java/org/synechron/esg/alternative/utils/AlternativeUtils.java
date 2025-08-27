package org.synechron.esg.alternative.utils;

import org.synechron.esg.model.CompanyESGScore;
import org.synechron.esg.model.Portfolio;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Alternative utils.
 */
public class AlternativeUtils {
    private static DecimalFormat df = new DecimalFormat("##.00");

    /**
     * Format double double.
     *
     * @param inputValue the input value
     * @return the double
     */
    public static Double formatDouble(Double inputValue) {
        Double outputValue = Double.parseDouble(df.format(0));
        if (inputValue != null)
            outputValue = Double.parseDouble(df.format(inputValue));
        return outputValue;

    }

    /**
     * Get isin list list.
     *
     * @param companyESGScoreList the company esg score list
     * @return the list
     */
    public static List<String> getISINList(List<CompanyESGScore> companyESGScoreList){
        return companyESGScoreList.stream().map(CompanyESGScore::getIsin).collect(Collectors.toList());
    }

    /**
     * Gets isin list.
     *
     * @param portfolio the portfolio
     * @return the isin list
     */
    public static List<String> getISINList(Portfolio portfolio) {
        return portfolio.getCompanies().stream().map(c -> c.getIsin()).collect(Collectors.toList());

    }

}
