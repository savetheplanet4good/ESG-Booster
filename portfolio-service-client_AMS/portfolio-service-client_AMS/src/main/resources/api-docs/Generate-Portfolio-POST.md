**Generate Portfolio**
----
	This api is basically developed so that we can connect to a remote url via username and password and upload a portfolio file from there.
	Currently is accepts an optional string paramter and picks the file to upload from the resources of the project.
*   **URL:**
    /portfolio-mgmt/generate-portfolio
*   **Method:**
    `POST`
*   **Path Params:**
    _None_
*   **Optional:**
	* **requestParam:**
	`"string"`
*   **Data Params:**
    _None_
*   **Sample:**
    /portfolio-mgmt/generate-portfolio
	* **requestParam:**
	`"demo"`
*   **Success Response:**
    * **Code:** 200 OK <br />
    * **Content:** 
    ```json
	{
	  "status": "OK",
	  "message": "Portfolio uploaded successfully.",
	  "errorMap": null
	}
    ```
*   **Error Response:**
    *   **Code:** 400 Bad Request <br />
    *   **Content:** 
	```json
	{
	  "status": "BAD_REQUEST",
	  "message": "Uploaded file contains incorrect company data records.",
	  "errorMap": {
		"Record 6": " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : CompanyModel(isin=US4781601046, name=Johnson & Johnson, wt=0.0, amountInvested=0)",
		"Record 5": " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : CompanyModel(isin=US2546871060, name=The Walt Disney Company, wt=0.0, amountInvested=0)",
		"Record 4": " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : CompanyModel(isin=US0605051046, name=Bank of America Corporation, wt=0.0, amountInvested=0)",
		"Record 3": " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : CompanyModel(isin=US0231351067, name=Amazon.com, Inc., wt=0.0, amountInvested=0)",
		"Record 2": " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : CompanyModel(isin=US00724F1012, name=Adobe Inc., wt=0.0, amountInvested=0)",
		"Record 1": " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : CompanyModel(isin=US0378331005, name=Apple Inc., wt=0.0, amountInvested=0)",
		"Record 10": " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : CompanyModel(isin=US46625H1005, name=JPMorgan Chase & Co., wt=0.0, amountInvested=0)",
		"Record 9": " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : CompanyModel(isin=US7427181091, name=The Procter & Gamble Company, wt=0.0, amountInvested=0)",
		"Record 8": " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : CompanyModel(isin=US7170811035, name=Pfizer Inc., wt=0.0, amountInvested=0)",
		"Record 7": " - Company record must contain data for atleast Weightage or Amount Invested. Data provided : CompanyModel(isin=US67066G1040, name=NVIDIA Corporation, wt=0.0, amountInvested=0)"
	  }
	}
	```
    *   **Sample Call:**
      ```javascript
        $.ajax({
          url: "/portfolio-mgmt/upload-portfolio",
          dataType: "json",
          type : "POST",
          success : function(r) {
            console.log(r);
          }
        });
      ```