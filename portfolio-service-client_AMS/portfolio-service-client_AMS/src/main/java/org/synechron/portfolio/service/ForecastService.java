package org.synechron.portfolio.service;

import org.synechron.portfolio.response.dto.FeatureImportanceResponse;
import org.synechron.portfolio.response.dto.ForecastResponse;
import java.io.IOException;

public interface ForecastService {

    public ForecastResponse getForecast(String isin, String investibleUniverseType) throws IOException;

    public FeatureImportanceResponse getFeatureImportance();
}
