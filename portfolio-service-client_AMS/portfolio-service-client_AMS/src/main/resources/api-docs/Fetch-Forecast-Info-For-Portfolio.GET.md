**Fetch Portfolio Forecast Details**
----
	Returns json data in form of ForecastResponse object which contains `predictions_without_sentiment`, `predictions_with_sentiment`, `actual` and `featureResponse` for the isin passed as input.
*   **URL:**
    /portfolio-mgmt/forecast/{isin}
*   **Method:**
    `GET`
*   **Path Params:**
    `{isin} = isin`
*   **Required:**
    `_{isin}_`
*   **Data Params:**
    _None_
*   **Sample:**
    /portfolio-mgmt/forecast/1
*   **Success Response:**
    * **Code:** 200 <br />
    * **Content:** 
    ```json
    {
	  "predictions_without_sentiment": null,
	  "predictions_with_sentiment": [
		{
		  "esgScore": 74.98,
		  "date": "2012-12-31",
		  "socialScore": 50.03,
		  "envScore": 85.05,
		  "govScore": 74.98
		},
		{
		  "esgScore": 45.89,
		  "date": "2013-12-31",
		  "socialScore": 50.84,
		  "envScore": 83.84,
		  "govScore": 45.89
		},
		{
		  "esgScore": 53.59,
		  "date": "2014-12-31",
		  "socialScore": 47.87,
		  "envScore": 79.65,
		  "govScore": 53.59
		},
		{
		  "esgScore": 65.09,
		  "date": "2015-12-31",
		  "socialScore": 53.26,
		  "envScore": 80.07,
		  "govScore": 65.09
		},
		{
		  "esgScore": 61.11,
		  "date": "2016-12-31",
		  "socialScore": 54.66,
		  "envScore": 82.22,
		  "govScore": 61.11
		},
		{
		  "esgScore": 74.64,
		  "date": "2017-12-31",
		  "socialScore": 69.59,
		  "envScore": 77.79,
		  "govScore": 74.64
		},
		{
		  "esgScore": 49.24,
		  "date": "2018-12-31",
		  "socialScore": 71.79,
		  "envScore": 76.29,
		  "govScore": 49.24
		},
		{
		  "esgScore": 45.36,
		  "date": "2019-12-31",
		  "socialScore": 68.89,
		  "envScore": 72.96,
		  "govScore": 45.36
		}
	  ],
	  "actual": [
		{
		  "esgScore": 48.01,
		  "date": "2011-12-31",
		  "socialScore": 54.22,
		  "envScore": 91.63,
		  "govScore": 91.48
		},
		{
		  "esgScore": 81.38,
		  "date": "2012-12-31",
		  "socialScore": 52.13,
		  "envScore": 88.95,
		  "govScore": 90.55
		},
		{
		  "esgScore": 49.81,
		  "date": "2013-12-31",
		  "socialScore": 52.98,
		  "envScore": 87.67,
		  "govScore": 74.48
		},
		{
		  "esgScore": 58.17,
		  "date": "2014-12-31",
		  "socialScore": 49.89,
		  "envScore": 83.29,
		  "govScore": 71.57
		},
		{
		  "esgScore": 70.65,
		  "date": "2015-12-31",
		  "socialScore": 55.5,
		  "envScore": 83.73,
		  "govScore": 87.07
		},
		{
		  "esgScore": 66.33,
		  "date": "2016-12-31",
		  "socialScore": 56.96,
		  "envScore": 85.98,
		  "govScore": 90.3
		},
		{
		  "esgScore": 81.02,
		  "date": "2017-12-31",
		  "socialScore": 72.52,
		  "envScore": 81.35,
		  "govScore": 84.54
		},
		{
		  "esgScore": 53.44,
		  "date": "2018-12-31",
		  "socialScore": 74.81,
		  "envScore": 79.78,
		  "govScore": 74.04
		}
	  ],
	  "featureResponse": {
		"esg_feature_importance": {
		  "ESGCombinedScore": 18.02,
		  "AggregatedAnnualNewsSentimentScore": 0.01,
		  "ESGCurrency": 0.85
		},
		"e_feature_importance": {
		  "EnvironmentPillarScore": 25.71,
		  "AggregatedAnnualNewsSentimentScore": 0.06,
		  "ESGCurrency": 0.66
		},
		"s_feature_importance": {
		  "AggregatedAnnualNewsSentimentScore": 0.04,
		  "SocialPillarScore": 23.31,
		  "ESGCurrency": 0.77
		},
		"g_feature_importance": {
		  "GovernancePillarScore": 18.62,
		  "AggregatedAnnualNewsSentimentScore": 0.03,
		  "ESGCurrency": 1.09
		}
	  }
	}
    ```
*   **Data Not Found:**
    *   **Code:** 200 OK <br />
    *   **Content:** 
		```json
		{
		  "predictions_without_sentiment": null,
		  "predictions_with_sentiment": null,
		  "actual": null,
		  "featureResponse": null
		}
		```