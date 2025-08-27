**Fetch Portfolios**
----
	Returns PortfolioDto object which contains few important properties of the portfolio against the id passed in input.
*   **URL:**
    /portfolio-mgmt/portfolio/{portfolioId}
*   **Method:**
    `GET`
*   **Path Params**
    `{portfolioId} = Portfolio Id`
*   **Required**
	_portfolioId_
*   **Sample:**
    /portfolio-mgmt/portfolio/1
*   **Success Response:**
    * **Code:** 200 OK <br />
    * **Content:** 
    ```json
	{
	  "portfolioId": "1",
	  "portfolioName": "Diversified Portfolio",
	  "portfolioType": "Default",
	  "investableUniverseType": "REFINITIV",
	  "esgCombinedScore": 54.46,
	  "esgScore": 70.19,
	  "envScore": 72.52,
	  "socialScore": 75.89,
	  "govScore": 62.31,
	  "environmentalFactors": null,
	  "socialFactors": null,
	  "governanceFactors": null,
	  "companyCount": 25,
	  "outLierCount": 25,
	  "fund": null,
	  "equity": null,
	  "calculationType": null,
	  "portfolioIsinsType": null
	}
    ```