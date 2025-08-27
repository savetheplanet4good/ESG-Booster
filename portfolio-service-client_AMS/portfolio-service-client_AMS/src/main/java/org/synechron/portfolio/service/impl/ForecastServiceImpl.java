package org.synechron.portfolio.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.synechron.esg.model.ApplicationComponentSettings;
import org.synechron.esg.model.CompanyESGScore;
import org.synechron.portfolio.application_settings.service.ApplicationComponentSettingsService;
import org.synechron.portfolio.constant.Constant;
import org.synechron.portfolio.response.dto.*;
import org.synechron.portfolio.service.ForecastService;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ForecastServiceImpl implements ForecastService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DataLakeServiceProxy dataLakeServiceProxy;

    @Autowired
    private ApplicationComponentSettingsService applicationComponentSettingsService;

    @Value("${forecast.url}")
    private String forecastUrl;


    private static DecimalFormat df = new DecimalFormat("##.00");

    @Override
    public ForecastResponse getForecast(String isin, String investibleUniverseType) throws IOException {

        List<Map<String, Object>> reqMap = new ArrayList<>();
        ForecastResponse forecast = new ForecastResponse();
        Map<String, ApplicationComponentSettings> applicationComponentSettingsMap =  applicationComponentSettingsService.getInitialApplicationComponentSettings();

        if(
                applicationComponentSettingsMap.get(Constant.IS_NEWS_ENABLED).getValue().equals(Boolean.TRUE) &&
                applicationComponentSettingsMap.get(Constant.IS_HISTORICAL_PERFORMANCE_ENABLED).getValue().equals(Boolean.TRUE) &&
                applicationComponentSettingsMap.get(Constant.IS_FORECAST_ENABLED).getValue().equals(Boolean.TRUE)
        ) {
            try {

                ResponseEntity<List<CompanyHistoricalDataForForecast>> historicalData = dataLakeServiceProxy.getHistoricalDataForForecast(isin, investibleUniverseType);
                List<HistoricalScores> historical_scores = new ArrayList<>();

                List<Forecast> actual = new ArrayList<>();

                for (CompanyHistoricalDataForForecast entryObj : historicalData.getBody()) {

                    actual.add(new Forecast(formatDouble(entryObj.getEsgCombinedScore()), entryObj.getDate(), formatDouble(entryObj.getEnvironmentalScore()), formatDouble(entryObj.getSocialScore()), formatDouble(entryObj.getGovernanceScore())));

                    historical_scores.add(new HistoricalScores(entryObj.getDate(), entryObj.getEsgScore(), entryObj.getSentimentScore(), entryObj.getEnvironmentalScore(), entryObj.getSocialScore(), entryObj.getGovernanceScore()));

                }

                //Keep only last year data. This change is put after forecast api updation to reduce response time
                actual.subList(0, actual.size() - 1).clear();
                historical_scores.subList(0, historical_scores.size() - 1).clear();

                ResponseEntity<CompanyESGScore> companyDataResponse = dataLakeServiceProxy.getCompanyData(isin);

                Optional<CompanyESGScore> companyData = Optional.of(companyDataResponse.getBody());
                String currency = companyData.get().getCurrencySymbol() != null ? "\\u" + Integer.toHexString(companyData.get().getCurrencySymbol().charAt(0) | 0x10000).substring(1) : "u00a3";
                Map<String, Object> map = new HashMap<>();
                map.put("TRBC Industry Group Name", companyData.get().getRefinitiveIndustryName());
                map.put("INSTRUMENT(NAME)", companyData.get().getTicker());
                map.put("ESG Currency", currency);
                map.put("historical_scores", historical_scores);
                if (Constant.DS1.equalsIgnoreCase(investibleUniverseType)) {
                    map.put("best_score", 100);
                } else if (Constant.DS2.equalsIgnoreCase(investibleUniverseType)) {
                    map.put("best_score", 0);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data source.");
                }
                reqMap.add(map);
                System.out.println(objectMapper.writeValueAsString(reqMap));

                final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
                final HttpClient httpClient = HttpClientBuilder.create()
                        .setRedirectStrategy(new LaxRedirectStrategy())
                        .build();
                factory.setHttpClient(httpClient);
                restTemplate.setRequestFactory(factory);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<List<Map<String, Object>>> entity = new HttpEntity<List<Map<String, Object>>>(reqMap, headers);
                Optional<Map<String, Object>> response = Optional.of(restTemplate.postForObject(forecastUrl + "esg_predict/", entity, Map.class));
                Optional<Map<String, Object>> resp = Optional.of((Map<String, Object>) response.get().get(companyData.get().getTicker()));
                List<Map<String, Object>> predictions_withSentiments = new ArrayList<>((List<Map<String, Object>>) resp.get().get("predictions"));
                List<Forecast> new_predictions_withSentiments = new ArrayList<>();
                List<Forecast> new_predictions_withOutSentiments = new ArrayList<>();
                for (Map<String, Object> forecastObj : predictions_withSentiments) {
                    new_predictions_withSentiments.add(
                            new Forecast(
                                    formatDouble(Double.parseDouble(forecastObj.get("ESG Score with Sentiment").toString())),
                                    forecastObj.get("Date").toString().split("T")[0] + "",
                                    formatDouble(Double.parseDouble(forecastObj.get("Environment Pillar Score with Sentiment").toString())),
                                    formatDouble(Double.parseDouble(forecastObj.get("Social Pillar Score with Sentiment").toString())),
                                    formatDouble(Double.parseDouble(forecastObj.get("Governance Pillar Score with Sentiment").toString()))
                            ));

                    new_predictions_withOutSentiments.add(
                            new Forecast(
                                    formatDouble(Double.parseDouble(forecastObj.get("ESG Score").toString())),
                                    forecastObj.get("Date").toString().split("T")[0] + "",
                                    formatDouble(Double.parseDouble(forecastObj.get("Environment Pillar Score").toString())),
                                    formatDouble(Double.parseDouble(forecastObj.get("Social Pillar Score").toString())),
                                    formatDouble(Double.parseDouble(forecastObj.get("Governance Pillar Score").toString()))
                            ));
                }

                //Sort in ascending by date
                new_predictions_withSentiments = new_predictions_withSentiments.stream().sorted(Comparator.comparing(Forecast::getDate))
                        .collect(Collectors.toList());

                //Sort in ascending by date
                new_predictions_withOutSentiments = new_predictions_withOutSentiments.stream().sorted(Comparator.comparing(Forecast::getDate))
                        .collect(Collectors.toList());

                actual = actual.stream().sorted(Comparator.comparing(Forecast::getDate))
                        .collect(Collectors.toList());

                forecast.setPredictions_without_sentiment(new_predictions_withOutSentiments);
                forecast.setPredictions_with_sentiment(new_predictions_withSentiments);
                forecast.setActual(actual);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return forecast;

    }

    @Override
    public FeatureImportanceResponse getFeatureImportance() {
        FeatureImportanceResponse response = null;

        try {

            ResponseEntity<FeatureImportanceResponse> resp = restTemplate.getForEntity(forecastUrl + "/feature_importance", FeatureImportanceResponse.class
            );

            response = resp.getBody();

            response = formatResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private Double formatDouble(Double inputValue) {
        Double outputValue = Double.parseDouble(df.format(0));
        if (inputValue != null)
            outputValue = Double.parseDouble(df.format(inputValue));
        return outputValue;

    }

    FeatureImportanceResponse formatResponse(FeatureImportanceResponse response) {

        Map<String, Double> featureWithSentiments = response.getEsg_feature_importance();
        Map<String,Double> envFeatureImportance = response.getE_feature_importance();
        Map<String,Double> socialFeatureImportance = response.getS_feature_importance();
        Map<String,Double> govFeatureImportance = response.getG_feature_importance();


        if (featureWithSentiments != null) {
            Map<String, Double> responseFeatureWithSentiments = new HashMap<>();
            for (Map.Entry entry : featureWithSentiments.entrySet()) {
                responseFeatureWithSentiments.put(entry.getKey().toString().replace(" ", ""), formatDouble(Double.parseDouble(entry.getValue().toString())));
            }
            response.setEsg_feature_importance(responseFeatureWithSentiments);
        }
        if (featureWithSentiments != null) {
            Map<String, Double> responseFeatureWithSentiments = new HashMap<>();
            for (Map.Entry entry : envFeatureImportance.entrySet()) {
                responseFeatureWithSentiments.put(entry.getKey().toString().replace(" ", ""), formatDouble(Double.parseDouble(entry.getValue().toString())));
            }
            response.setE_feature_importance(responseFeatureWithSentiments);
        }
        if (featureWithSentiments != null) {
            Map<String, Double> responseFeatureWithSentiments = new HashMap<>();
            for (Map.Entry entry : socialFeatureImportance.entrySet()) {
                responseFeatureWithSentiments.put(entry.getKey().toString().replace(" ", ""), formatDouble(Double.parseDouble(entry.getValue().toString())));
            }
            response.setS_feature_importance(responseFeatureWithSentiments);
        }
        if (featureWithSentiments != null) {
            Map<String, Double> responseFeatureWithSentiments = new HashMap<>();
            for (Map.Entry entry : govFeatureImportance.entrySet()) {
                responseFeatureWithSentiments.put(entry.getKey().toString().replace(" ", ""), formatDouble(Double.parseDouble(entry.getValue().toString())));
            }
            response.setG_feature_importance(responseFeatureWithSentiments);
        }


        return response;
    }
}
