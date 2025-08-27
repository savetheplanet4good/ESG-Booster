package org.synechron.esg.masteruniverse.utils;

import java.text.DecimalFormat;

public class MasterUniverseUtils {

    private static DecimalFormat df = new DecimalFormat("##.00");

    public static Double formatDouble(Double inputValue) {
        Double outputValue = Double.parseDouble(df.format(0));
        if (inputValue != null)
            outputValue = Double.parseDouble(df.format(inputValue));
        return outputValue;

    }
}
