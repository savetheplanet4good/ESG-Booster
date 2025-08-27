**Update Master-universe**
----
	Updates the values of filter parameters of Investable universe filter according to the input passed.
*   **URL:**
    /master-universe/update-investible-universe/{portfolioId}
*   **Method:**
    `POST`
*   **Path Params:**
    {portfolioId} = Portfolio Id
*   **Required:**
    _{portfolioId}_
	* **requestBody:**
	```json
	{
	  "environmental": 0,
	  "esg": 0,
	  "governance": 0,
	  "selectedCountries": [
		"string"
	  ],
	  "selectedSectors": [
		"string"
	  ],
	  "social": 0
	}
	```
*   **Sample:**
    /master-universe/update-investible-universe/1
	* **requestBody:** 
	```json
	{
	  "environmental": 40,
	  "esg": 50,
	  "governance": 60,
	  "selectedCountries": [
		  "USA",
		  "Germany",
		  "Great Britain",
		  "Cayman Islands",
		  "Spain",
		  "France",
		  "Sweden",
		  "Canada",
		  "Norway",
		  "Italy",
		  "Belgium",
		  "Ireland",
		  "Austria",
		  "Switzerland",
		  "Japan",
		  "Denmark",
		  "Taiwan, Province of China",
		  "Netherlands",
		  "China",
		  "Turkey",
		  "Thailand",
		  "Finland",
		  "Indonesia",
		  "Philippines",
		  "Chile",
		  "Israel",
		  "Guernsey",
		  "Portugal",
		  "Hong Kong",
		  "Singapore",
		  "Bermuda",
		  "Australia",
		  "Russian Federation",
		  "South Africa",
		  "Brazil",
		  "Liberia",
		  "Peru",
		  "Jersey",
		  "Panama",
		  "Luxembourg",
		  "Netherlands Antilles",
		  "Mexico",
		  "Virgin Islands (British)",
		  "South Korea"
		],
		"selectedSectors": [
		  "Industrials",
		  "Information Technology",
		  "Real Estate",
		  "HealthCare",
		  "Materials",
		  "Consumer Discretionary",
		  "Energy",
		  "Financials",
		  "Communication Services",
		  "Utilities",
		  "Consumer Staples"
		],
	  "social": 70
	}
	```
*   **Success Response:**
    * **Code:** 200 OK <br />
    * **Content:** 
    ```json
    {
	  "status": "SUCCESS",
	  "message": "Record Updated Successfully"
	}
    ```