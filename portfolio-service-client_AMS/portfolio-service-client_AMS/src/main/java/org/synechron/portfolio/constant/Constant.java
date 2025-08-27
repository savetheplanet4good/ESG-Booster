package org.synechron.portfolio.constant;

import java.util.*;

/**
 * The type Constant.
 */
public class Constant {

    private Constant() {
        throw new IllegalStateException("Constants class");
    }

    /**
     * The constant MINIO_BUCKET_NAME.
     */
    public static final String MINIO_BUCKET_NAME ="investtech-esg-portfoliofile";

    /**
     * The constant PORTFOLIO_NAME.
     */
    public static final String PORTFOLIO_NAME ="Portfolio";

    /**
     * The constant PORTFOLIO_TYPE_DEFAULT.
     */
    public static final String PORTFOLIO_TYPE_DEFAULT ="Default";

    /**
     * The constant PORTFOLIO_TYPE_NEW.
     */
    public static final String PORTFOLIO_TYPE_NEW ="New";

    /**
     * The constant REFINITIV.
     */
    public static final String REFINITIV ="REFINITIV";

    /**
     * The constant NORMALIZED.
     */
    public static final String NORMALIZED="NORMALIZED";

    /**
     * The constant SUSTAINALYTICS.
     */
    public static final String SUSTAINALYTICS="SUSTAINALYTICS";

    /**
     * The constant REFINITIV_INVESTIBLE_UNVIERSE_TYPE.
     */
    public static final String REFINITIV_INVESTIBLE_UNVIERSE_TYPE ="REFINITIV";

    /**
     * The constant SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE.
     */
    public static final String SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE ="SUSTAINALYTICS";

    /**
     * The constant MSCI_INVESTIBLE_UNVIERSE_TYPE.
     */
    public static final String MSCI_INVESTIBLE_UNVIERSE_TYPE ="MSCI";

    public static final String DS1 ="DS1";

    public static final String DS2 ="DS2";
    /**
     * The constant DEFAULT_PORTFOLIO_API_UPLOAD_FILE_NAME.
     */
    public static final String DEFAULT_PORTFOLIO_API_UPLOAD_FILE_NAME = "Funds Portfolio.csv";

    /**
     * The constant DEFAULT_PORTFOLIO_ID_REFINITIV.
     */
    public static final String DEFAULT_PORTFOLIO_ID_REFINITIV = "1";
    /**
     * The constant DEFAULT_PORTFOLIO_REFINITIV_FILE_NAME.
     */
    public static final String DEFAULT_PORTFOLIO_REFINITIV_FILE_NAME = "default-portfolio-refinitiv.csv";
    /**
     * The constant DEFAULT_PORTFOLIO_NAME_REFINITIV.
     */
    public static final String DEFAULT_PORTFOLIO_NAME_REFINITIV = "Diversified Portfolio";

    /**
     * The constant DEFAULT_PORTFOLIO_ID_SUSTAINALYTICS.
     */
    public static final String DEFAULT_PORTFOLIO_ID_SUSTAINALYTICS = "2";
    /**
     * The constant DEFAULT_PORTFOLIO_SUSTAINALYTICS_FILE_NAME.
     */
    public static final String DEFAULT_PORTFOLIO_SUSTAINALYTICS_FILE_NAME = "default-portfolio-sustainalytics.csv";
    /**
     * The constant DEFAULT_PORTFOLIO_NAME_SUSTAINALYTICS.
     */
    public static final String DEFAULT_PORTFOLIO_NAME_SUSTAINALYTICS = "Mid Cap Portfolio";
    /**
     * The constant UNDERSCORE.
     */
    public static final String UNDERSCORE = "_";
    /**
     * The constant UPLOAD_FILE_TYPE.
     */
    public static final String UPLOAD_FILE_TYPE = "text/csv";
    /**
     * The constant DEFAULT_PORTFOLIOS_REF_SUST_MAP.
     */
    public static final Map<String, List<String>> DEFAULT_PORTFOLIOS_REF_SUST_MAP = new HashMap<String, List<String>>(){
        {
            put(DEFAULT_PORTFOLIO_REFINITIV_FILE_NAME, new ArrayList<String>(){
                {
                    add(DEFAULT_PORTFOLIO_ID_REFINITIV);
                    add(DEFAULT_PORTFOLIO_NAME_REFINITIV);
                    add(REFINITIV_INVESTIBLE_UNVIERSE_TYPE);
                }
            });
            put(DEFAULT_PORTFOLIO_SUSTAINALYTICS_FILE_NAME, new ArrayList<String>(){
                {
                    add(DEFAULT_PORTFOLIO_ID_SUSTAINALYTICS);
                    add(DEFAULT_PORTFOLIO_NAME_SUSTAINALYTICS);
                    add(SUSTAINALYTICS_INVESTIBLE_UNVIERSE_TYPE);
                }
            });
        }
    };

    /**
     * The constant UPDATE_NAME_PORTFOLIO.
     */
    public static final String UPDATE_NAME_PORTFOLIO = "update-name-portfolio";
    /**
     * The constant UPDATE_INVESTIBLE_UNIVERSE_PORTFOLIO.
     */
    public static final String UPDATE_INVESTIBLE_UNIVERSE_PORTFOLIO = "update-investible-universe-portfolio";
    /**
     * The constant NO_OF_BENCHMARKS.
     */
    public static final Integer NO_OF_BENCHMARKS = 8;

    /**
     * The constant FUND_VALUE.
     */
    public static final String FUND_VALUE = "Fund";
    /**
     * The constant FUND.
     */
    public static final String FUND ="Fund";
    /**
     * The constant EQUITY.
     */
    public static final String EQUITY ="Equity";
    /**
     * The constant NEWS_SENTIMENT_FOR_NO_OF_MONTHS.
     */
    public static final Integer NEWS_SENTIMENT_FOR_NO_OF_MONTHS = 0;
    /**
     * The constant SECTORS_INSIGHT_TITLE.
     */
    public static final String SECTORS_INSIGHT_TITLE = "Sector";
    /**
     * The constant SECTORS_INSIGHT_MESSAGE.
     */
    public static final String SECTORS_INSIGHT_MESSAGE = "There are %d sectors out of %d..";
    /**
     * The constant NEWS_INSIGHT_TITLE.
     */
    public static final String NEWS_INSIGHT_TITLE = "News Sentiment";
    /**
     * The constant NEWS_INSIGHT_MESSAGE.
     */
    public static final String NEWS_INSIGHT_MESSAGE = "There are %d companies with -ve impacting news and %d companies with +ve impacting news.";
    /**
     * The constant INFLUENCES_INSIGHT_TITLE.
     */
    public static final String INFLUENCES_INSIGHT_TITLE = "Influence Score";
    /**
     * The constant INFLUENCES_INSIGHT_MESSAGE.
     */
    public static final String INFLUENCES_INSIGHT_MESSAGE = "There are %d companies have -ve influence on the overall portfolio and %d companies have +ve influence on the overall portfolio in terms of ESG.";
    /**
     * The constant OUTLIERS_INSIGHT_TITLE.
     */
    public static final String OUTLIERS_INSIGHT_TITLE = "Outlier";
    /**
     * The constant OUTLIERS_INSIGHT_MESSAGE.
     */
    public static final String OUTLIERS_INSIGHT_MESSAGE = "Portfolio outlier range is %d - %d.";
    /**
     * The constant ESG_PERCENT_CHANGE_TITLE.
     */
    public static final String ESG_PERCENT_CHANGE_TITLE = "ESG Score";
    /**
     * The constant ESG_PERCENT_CHANGE_MESSAGE.
     */
    public static final String ESG_PERCENT_CHANGE_MESSAGE = "Percentage change in portfolios's total ESG score is %s";
    /**
     * The constant ESG_SINGLE_COMPONENT_TITLE.
     */
    public static final String ESG_SINGLE_COMPONENT_TITLE = "Single Component Effect";
    /**
     * The constant ESG_SINGLE_COMPONENT_MESSAGE.
     */
    public static final String ESG_SINGLE_COMPONENT_MESSAGE = "Highest Percentage change at company level: %s ESG:%s , E: %s , S:%s , G:%s" ;
    /**
     * The constant OUTLIER_PERCENTILE.
     */
    public static final Integer OUTLIER_PERCENTILE = 30;
    /**
     * The constant APPLICATION_SETTINGS_CACHE.
     */
    public static final String APPLICATION_SETTINGS_CACHE = "applicationSettings";
    /**
     * The constant DEFAULT_APPLICATION_COMPONENT_SETTINGS_FILE_NAME.
     */
    public static final String DEFAULT_APPLICATION_COMPONENT_SETTINGS_FILE_NAME = "default-application-component-settings.json";
    /**
     * The constant IS_FORECAST_ENABLED.
     */
    public static final String IS_FORECAST_ENABLED = "isForecastEnabled";
    /**
     * The constant IS_CO2_EMISSION_ENABLED.
     */
    public static final String IS_CO2_EMISSION_ENABLED = "isCO2emissionEnabled";
    /**
     * The constant IS_NEWS_ENABLED.
     */
    public static final String IS_NEWS_ENABLED = "isNewsEnabled";
    /**
     * The constant IS_PEER_COMPARISON_ENABLED.
     */
    public static final String IS_PEER_COMPARISON_ENABLED = "isPeerComparisonEnabled";
    /**
     * The constant IS_SIMULATION_ENABLED.
     */
    public static final String IS_SIMULATION_ENABLED = "isSimulationEnabled";
    /**
     * The constant IS_BENCHMARK_ENABLED.
     */
    public static final String IS_BENCHMARK_ENABLED = "isBenchmarkEnabled";
    /**
     * The constant IS_OUTLIER_ENABLED.
     */
    public static final String IS_OUTLIER_ENABLED = "isOutliersEnabled";
    /**
     * The constant IS_SOCIAL_SENTIMENT_ENABLED.
     */
    public static final String IS_SOCIAL_SENTIMENT_ENABLED = "isSocialSentimentsEnabled";
    /**
     * The constant IS_HISTORICAL_PERFORMANCE_ENABLED.
     */
    public static final String IS_HISTORICAL_PERFORMANCE_ENABLED = "isCompanyHistPerformanceEnabled";
    public static final String IS_INVEST_SUIT_ENABLED = "isInvestsuitEnabled";

    public static final String DS3 ="DS3";
    
    public static final String NR ="NR";

    public static final String A ="A";

    public static final String AA ="AA";

    public static final String AAA ="AAA";

    public static final String B ="B";

    public static final String BB ="BB";

    public static final String BBB ="BBB";

    public static final String CCC ="CCC";
}
