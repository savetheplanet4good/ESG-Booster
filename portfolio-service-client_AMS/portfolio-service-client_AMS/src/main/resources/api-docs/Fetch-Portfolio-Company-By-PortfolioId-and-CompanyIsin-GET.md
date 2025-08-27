**Fetch Portfolio Company**
----
	Returns CompanyDto object which contains important information of the company against the isin and portfolioId passed in input.
*   **URL:**
    /portfolio-mgmt/company/{portfolioId}/{isin}
*   **Method:**
    `GET`
*   **Path Params:**
    `{isin} = isin`
	`{portfolioId} = portfolioId`
*   **Required:**
	_isin_
	_portfolioId_
*   **Sample:**
    /portfolio-mgmt/company/1/US0028241000
*   **Success Response:**
    * **Code:** 200 OK <br />
    * **Content:**
    ```json
	{
	  "isin": "US0028241000",
	  "name": "Abbott Laboratories",
	  "wt": 0,
	  "esgScore": 76.89,
	  "environmentalScore": 74.81,
	  "socialScore": 79.78,
	  "governenceScore": 74.04,
	  "holdingValue": 3,
	  "controversyScore": 30,
	  "esgCombinedScore": 53.44,
	  "totalESGCombinedScore": 1.6,
	  "totalESGScore": 2.31,
	  "totalEnvironmentalScore": 2.24,
	  "totalSocialScore": 2.39,
	  "totalGovernanceScore": 2.22,
	  "influenceESGCombinedScore": -0.06,
	  "influenceESGSCore": 0.29,
	  "influenceEnvironmentalScore": 0.09,
	  "influenceSocialScore": 0.15,
	  "influenceGovernanceScore": 0.57,
	  "environmentalFactors": [
		{
		  "name": "Emission",
		  "score": 93.37
		},
		{
		  "name": "Environmental Innovation",
		  "score": 50
		},
		{
		  "name": "Resource Use",
		  "score": 82.63
		}
	  ],
	  "socialFactors": [
		{
		  "name": "Community",
		  "score": 97.38
		},
		{
		  "name": "Human Rights",
		  "score": 54.08
		},
		{
		  "name": "Product Responsibility",
		  "score": 75.9
		},
		{
		  "name": "WorkForce",
		  "score": 96.8
		}
	  ],
	  "governanceFactors": [
		{
		  "name": "CSR Strategy",
		  "score": 83.48
		},
		{
		  "name": "Management Score",
		  "score": 68.58
		},
		{
		  "name": "Shareholders",
		  "score": 85.98
		}
	  ],
	  "news": null,
	  "outlierScore": 12,
	  "refinitivNormalisedScore": 0.448,
	  "sustainalyticsNormalisedScore": 0.332,
	  "refinitivESGCombinedScore": 53.444,
	  "sustainalyticsESGCombinedScore": 30,
	  "isOutLier": false,
	  "isinType": null
	}
    ```