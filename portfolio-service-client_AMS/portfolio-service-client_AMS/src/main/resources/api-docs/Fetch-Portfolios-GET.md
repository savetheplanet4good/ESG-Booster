**Fetch Portfolios**
----
	Returns PortfolioListResponse object which is a list of all portfolios with name and id that exists in the application.
*   **URL:**
    /portfolio-mgmt/portfolios
*   **Method:**
    `GET`
*   **Sample:**
    /portfolio-mgmt/portfolios
*   **Success Response:**
    * **Code:** 200 OK <br />
    * **Content:** 
    ```json
	{
	  "portfolios": [
		{
		  "portfolioId": "1",
		  "portfolioName": "Diversified Portfolio"
		},
		{
		  "portfolioId": "2",
		  "portfolioName": "Mid Cap Portfolio"
		}
	  ]
	}
    ```