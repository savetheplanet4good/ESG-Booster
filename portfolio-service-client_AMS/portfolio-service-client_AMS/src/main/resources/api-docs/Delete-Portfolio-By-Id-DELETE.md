**Delete Portfolio**
----
	Deleted the portfolio whose id is passed as input.
*   **URL**
    /porfolio-mgmt/portfolio/{portfolioId}
*   **Method:**
    `DELETE`
*   **Path Params**
    `{portfolioId} = Portfolio Id`
*   **Required:**
    _{portfolioId}_
*   **Data Params**
    _None_
*   **Sample**
    /porfolio-mgmt/portfolio/1
*   **Success Response:**
    * **Code:** 200 OK <br />
    * **Content:** 
    ```json
    {
	  "status": "OK",
	  "message": "Portfolio Deleted Successfully."
	}
    ```