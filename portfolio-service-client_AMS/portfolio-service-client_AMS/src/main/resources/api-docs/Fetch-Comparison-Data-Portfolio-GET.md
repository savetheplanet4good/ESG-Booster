**Fetch Comparison Data for Portfolio**
----
	Returns ComparisonResponse object against the portfolio whose is passed as input. This api get hit on the simulation page for portfolio vs simulation feature.
*   **URL:**
    /portfolio-mgmt/comparison/{portfolioId}
*   **Method:**
    `GET`
*   **Path Params:**
    `{portfolioId} = Portfolio Id`
*   **Required:**
    _{portfolioId}_
*   **Sample:**
    /portfolio-mgmt/comparison/1
*   **Success Response:**
    * **Code:** 200 OK <br />
    * **Content:** 
    ```json
	{
	  "sector": {
		"sectors": {
		  "Utilities": 4,
		  "Energy": 4,
		  "Consumer Staples": 4,
		  "Real Estate": 4,
		  "Financials": 12,
		  "Industrials": 20,
		  "HealthCare": 28,
		  "Consumer Discretionary": 4,
		  "Information Technology": 20
		}
	  },
	  "regions": [
		"IE",
		"US"
	  ],
	  "esgScore": 70.19,
	  "envScore": 72.52,
	  "socialScore": 75.89,
	  "govScore": 62.31,
	  "esgCombinedScore": 54.46,
	  "currency": [
		"USD"
	  ],
	  "currencyAllocation": {
		"currencyAllocation": {
		  "USD": 100
		}
	  },
	  "countryAllocation": {
		"countryAllocation": {
		  "IE": 8,
		  "US": 92
		}
	  }
	}
    ```