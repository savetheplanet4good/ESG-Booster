**News Latest Top 1**
----
	Returns json data of top 1 latest news article for list of company's isin passed as the input.
*   **URL:**
    /news/latest/news
*   **Method:**
    `POST`
*   **Path Params:**
    _None_
*   **Required:**
    `list of isin=[string]`
*   **Data Params:**
    _None_
*   **Sample:**
    /news/latest/news
	* **requestBody:** ["US79466L3024", "US0311621009"]
*   **Success Response:**
    * **Code:** 200 <br />
    * **Content:** 
    ```json
    {
	  "US0311621009": {
		"source": "MedPageToday.com",
		"type": "donation",
		"sentiment": 0.26,
		"title": "LDL Could Inform Revascularization Choices in Diabetes",
		"description": "Reported receiving research grants from Amgen",
		"time": "2020-12-08T11:22:24.000+00:00",
		"isin": "US0311621009",
		"subType": ""
	  },
	  "US79466L3024": {
		"source": "Business Insider",
		"type": "executive-resignation",
		"sentiment": -0.57,
		"title": "Salesforce's chief financial officer Mark Hawkins is stepping down and will be replaced by chief legal officer Amy Weaver",
		"description": "Salesforce's chief financial officer Mark Hawkins is stepping down",
		"time": "2020-12-08T11:22:24.000+00:00",
		"isin": "US79466L3024",
		"subType": ""
	  }
	}
	```
*   **Data Not Found:**
    * **Code:** 400 Bad Request <br />
    * **Content:** `{ "msg" : "Error Occured while retrieving latest news data from Datalake" }`

*   **Error Response:**
    *   **Code:** 400 Bad Request <br />
    *   **Content:** `{ "msg" : "Error Occured while retrieving latest news data from Datalake" }`
    *   **Sample Call:**
      ```javascript
        $.ajax({
          url: "/news/latest/news",
		  requestBody : ["US79466L3024", "US0311621009"]
          dataType: "json",
          type : "POST",
          success : function(r) {
            console.log(r);
          }
        });
      ```