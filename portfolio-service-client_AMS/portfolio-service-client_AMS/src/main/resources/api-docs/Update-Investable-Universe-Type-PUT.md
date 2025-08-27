**Update portfolio's Investable-universe type**
----
	This api updates the investable-universe type property of a porfolio which accepts UpdateInvestibleUniverseTypeDTO object which contains portfolioId to be updated in it.
*   **URL:**
    /portfolio-mgmt/investableUniverseType-portfolio
*   **Method:**
    `PUT`
*   **Path Params:**
    _None_
*   **Sample:**
    /portfolio-mgmt/investableUniverseType-portfolio
*   **Required:**
    * **requestBody:**
	```json
	{
	  "investibleUniverseType": "string",
	  "portfolioId": "string"
	}
	```
*   **Data Params:**
    _None_
*   **Sample:**
	* **requestBody:**
	```json
	{
	  "investibleUniverseType": "Refinitiv",
	  "portfolioId": "1"
	}
	```
*   **Success Response:**
    * **Code:** 200 <br />
    *    **Content:** 
    ```json
    {
	  "status": "OK",
	  "message": "Portfolio switched to Refinitiv Scoring Methodology."
	}
    ```