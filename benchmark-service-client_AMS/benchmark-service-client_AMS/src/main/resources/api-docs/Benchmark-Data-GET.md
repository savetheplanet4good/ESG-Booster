**Benchmark Data**
----
	Returns list of benchmark object with source, indexName, indexTotalScore, envIndexScore, socialIndexScore, govIndexScore fields.
*   **URL:**
    /benchmark/benchmark-data	
*   **Method:**
    `GET`
*   **Path Params:**
    There are no input paramters to this api, the required input to execute the query are configured in the configuration file i.e. the properties file.
*   **Required:**
    Set following properties in the porperties file.
	* **list.benchmark.datasource**=`Refinitiv,Sustainalytics` <br />
	* **list.benchmark.index**=`DOW_JONES30,NASDAQ100,S&P500,EURO_Stocks`
*   **Data Params:**
    _None_
*   **Sample:**
    /benchmark/benchmark-data
*   **Success Response:**
    * **Code:** 200 <br />
    * **Content:** 
    ```json
    [
	  {
		"source": "Refinitiv",
		"indexName": "DOW JONES30",
		"indexTotalScore": 51.76,
		"envIndexScore": 73.62,
		"socialIndexScore": 78.8,
		"govIndexScore": 67.27
	  },
	  {
		"source": "Sustainalytics",
		"indexName": "DOW JONES30",
		"indexTotalScore": 24.14,
		"envIndexScore": 4.28,
		"socialIndexScore": 13.36,
		"govIndexScore": 8.81
	  },
	  {
		"source": "Refinitiv",
		"indexName": "NASDAQ100",
		"indexTotalScore": 43.38,
		"envIndexScore": 56.47,
		"socialIndexScore": 68.37,
		"govIndexScore": 65.98
	  },
	  {
		"source": "Sustainalytics",
		"indexName": "NASDAQ100",
		"indexTotalScore": 28.99,
		"envIndexScore": 11.21,
		"socialIndexScore": 18.9,
		"govIndexScore": 16.32
	  },
	  {
		"source": "Refinitiv",
		"indexName": "S&P500",
		"indexTotalScore": 47.19,
		"envIndexScore": 55.65,
		"socialIndexScore": 66.65,
		"govIndexScore": 61.56
	  },
	  {
		"source": "Sustainalytics",
		"indexName": "S&P500",
		"indexTotalScore": 26.7,
		"envIndexScore": 8.63,
		"socialIndexScore": 15.43,
		"govIndexScore": 12.58
	  },
	  {
		"source": "Refinitiv",
		"indexName": "EURO Stocks",
		"indexTotalScore": 61.79,
		"envIndexScore": 80.27,
		"socialIndexScore": 86.27,
		"govIndexScore": 69.77
	  },
	  {
		"source": "Sustainalytics",
		"indexName": "EURO Stocks",
		"indexTotalScore": 19.96,
		"envIndexScore": 4.61,
		"socialIndexScore": 7.71,
		"govIndexScore": 7.68
	  }
	]
    ```	
*   **Error Response:**
    *   **Code:** 400 Bad Request <br />
    *   **Response Body:** `{ "msg" : "Error Occured while retrieving benchmark data from Datalake" }`
    *   **Sample Call:**
      ```javascript
        $.ajax({
          url: "/benchmark/benchmark-data",
          dataType: "json",
          type : "GET",
          success : function(r) {
            console.log(r);
          }
        });
      ```