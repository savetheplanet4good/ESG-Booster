**Clone Portfolio**
----
	It clones a similar portfolio which is passed in as input.
	This api takes input as ClonePortfolioRequest object which contains `portfolioId` which is to be cloned and `portfolioName` which will the name assigned to the cloned portfolio.
*   **URL:**
    /portfolio-mgmt/clone-portfolio
*   **Method:**
    `POST`
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
*   **Sample:**
    /portfolio-mgmt/clone-portfolio
	* **requestBody:**
	```json
	{
	  "portfolioId": "1",
	  "portfolioName": "Cloned Portfolio"
	}
	```
*   **Success Response:**
    * **Code:** 200 OK <br />
    * **Content:** 
    ```json
	{
	  "status": "OK",
	  "message": "Portfolio Cloned Successfully."
	}
    ```
*   **Error Response:**
    *   **Code:** 500 Internal Server Error <br />
    *   **Content:** `{ "msg" : "Exception occurred. Please Contact System Admin" }`
    *   **Sample Call:**
      ```javascript
        $.ajax({
          url: "/portfolio-mgmt/clone-portfolio",
          dataType: "json",
          type : "POST",
          success : function(r) {
            console.log(r);
          }
        });
      ```