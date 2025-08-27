**Rename Portfolio**
----
	It renames the portfolio with the new name passed in the input.
	This api takes input as RenamePortfolioDto object which contains `portfolioId` which is to be renamed and `portfolioName` which will the name assigned to the portfolio.
*   **URL:**
    /portfolio-mgmt/rename-portfolio
*   **Method:**
    `PUT`
*   **Path Params:**
    _None_
*   **Required:**
    * **requestBody:**
	```json
	{
	  "portfolioId": "string",
	  "portfolioName": "string"
	}
	```
*   **Data Params:**
    _None_
*   **Sample:**
    /portfolio-mgmt/rename-portfolio
	* **requestBody:**
	```json
	{
	  "portfolioId": "1",
	  "portfolioName": "Renamed Portfolio"
	}
	```
*   **Success Response:**
    * **Code:** 200 <br />
    *    **Content:** 
    ```json
    {
	  "status": "OK",
	  "message": "Portfolio Renamed Successfully."
	}
    ```
*   **Duplicate Name Error Response:**
    *   **Code:** 400 Bad Request <br />
    *   **Content:** `{ "msg" : "Portfolio Name 'demo' already exists. Please try with different Portfolio Name." }`
    *   **Sample Call:**
      ```javascript
        $.ajax({
          url: "/portfolio-mgmt/rename-portfolio",
          dataType: "json",
          type : "GET",
          success : function(r) {
            console.log(r);
          }
        });
      ```