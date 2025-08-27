package org.synechron.news.utils;

import java.text.DecimalFormat;
import java.util.UUID;

/**
 *
 * Class Name : NewsUtilities
 * Purpose : This class contains common utility methods of this service.
 *
 */
public final class NewsUtilities {

    /**
     *
     * Function Name : getRandomStringId()
     * Purpose : Generate a random string id.
     *
     */
    public static String getRandomStringId() {
        UUID uid = UUID.randomUUID();
        return uid.toString();
    }

    /**
     *
     * Function Name : normaliseDecimals()
     * Purpose : Normalise double value to 2 precisions.
     * @param value : String to be normalised.
     *
     */
    public static Double normaliseDecimals(Double value) {
        Double normalisedValue = 0.0;
        DecimalFormat df = new DecimalFormat("##.00");
        if (value != null)
            normalisedValue = Double.parseDouble(df.format(value));
        return normalisedValue;
    }
}
