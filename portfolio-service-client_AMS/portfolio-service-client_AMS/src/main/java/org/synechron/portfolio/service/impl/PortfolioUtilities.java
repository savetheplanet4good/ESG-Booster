package org.synechron.portfolio.service.impl;

import com.hazelcast.core.IMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.model.Portfolio;

import java.text.DecimalFormat;
import java.util.*;

public final class PortfolioUtilities {

    private static Random rand = new Random();

    public static Double normaliseDecimals(Double value) {
        Double normalisedValue = 0.0;
        DecimalFormat df = new DecimalFormat("##.00");
        if (value != null)
            normalisedValue = Double.parseDouble(df.format(value));
        return normalisedValue;
    }


    public static String randomPortfolioIdGenerator() {

        return String.format("%05d", rand.nextInt(100000));
    }


    public static Double percentile(List<Double> latencies, double percentile) {
        Collections.sort(latencies);
        int index = (int) Math.ceil(percentile / 100.0 * latencies.size());
        return latencies.get(index - 1);
    }

    public static String getCustomPortfolioName(String createPortfolioName, IMap<String, Portfolio> dataStorePortfolio) {

        List<String> portfolioNames = new ArrayList<>();
        String portfolioName = "";

        dataStorePortfolio.loadAll(Boolean.TRUE);

        if (dataStorePortfolio.size() > 0 && !dataStorePortfolio.isEmpty()) {
            for (Map.Entry<String, Portfolio> entry : dataStorePortfolio.entrySet()) {
                portfolioNames.add(dataStorePortfolio.get(entry.getKey()).getPortfolioName());
            }
            for (int i = 1; i <= dataStorePortfolio.size(); i++) {
                portfolioName = createPortfolioName + " " + i;
                if (!portfolioNames.contains(portfolioName)) {
                    return portfolioName;
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Portfolio's cache is Empty.");
        }
        return portfolioName;
    }

    public static Double findPercentage(Double companiesWithSameEsgMsciScoreSum, Double noOfPortfolioCompaniesWithSameEsgMsciScore){
        Double average = companiesWithSameEsgMsciScoreSum / noOfPortfolioCompaniesWithSameEsgMsciScore;
        return PortfolioUtilities.normaliseDecimals(average.isNaN() ? 0.0 : average);
    }
}
