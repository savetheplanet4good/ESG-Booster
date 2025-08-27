**News Feeds Top 5**
----
	Returns json data of top 5 news articles for a specific company isin passed as the input.
*   **URL:**
    /news/company-news-feeds
*   **Method:**
    `POST`
*   **Path Params:**
    _None_
*   **Required:**
    `isin=[string]`
*   **Data Params:**
    _None_
*   **Sample:**
    /news/company-news-feeds
	* **requestBody:** US79466L3024
*   **Success Response:**
    * **Code:** 200 <br />
    * **Content:** 
    ```json
    {
	  "isin": "US79466L3024",
	  "newsdata": [
		{
		  "source": "Livemint",
		  "type": "",
		  "sentiment": -0.58,
		  "title": "Salesforce's deal for Slack faces post-pandemic test",
		  "description": "Virus first struck, Salesforce",
		  "url": "https://www.livemint.com/companies/news/salesforce-s-deal-for-slack-faces-post-pandemic-test-11607060766331.html",
		  "time": "2020-12-08T11:22:24.000+00:00"
		},
		{
		  "source": "Dow Jones Newswires",
		  "type": "",
		  "sentiment": -0.58,
		  "title": "Salesforce's Deal for Slack Faces Post-Pandemic Test",
		  "description": "Virus first struck, Salesforce",
		  "url": "https://app.ravenpack.com/search/#q=63912F6E3A247A643399796882338759",
		  "time": "2020-12-08T11:22:24.000+00:00"
		},
		{
		  "source": "MSN",
		  "type": "",
		  "sentiment": -0.49,
		  "title": "Salesforce to buy workplace app Slack in $27.7 billion deal",
		  "description": "Salesforce also said Chief Financial Officer Mark Hawkins would retire in January",
		  "url": "https://www.msn.com/en-gb/money/other/salesforce-to-buy-workplace-app-slack-in-27-7-billion-deal/ar-BB1bxw7S",
		  "time": "2020-12-08T11:22:24.000+00:00"
		},
		{
		  "source": "TheStreet",
		  "type": "",
		  "sentiment": 0.46,
		  "title": "How to Trade Salesforce With Earnings, Slack Deal on Deck",
		  "description": "Salesforce settled into a trading range, with resistance at $270",
		  "url": "https://www.thestreet.com/investing/salesforce-crm-stock-earnings-preview-slack-work-acquisition",
		  "time": "2020-12-08T11:22:24.000+00:00"
		},
		{
		  "source": "MarketScreener",
		  "type": "",
		  "sentiment": 0.26,
		  "title": "BUSINESS AS A PLATFORM FOR CHANGE : Celebrating Three Salesforce Customers Making an Impact",
		  "description": "Salesforce donated 16,000 of these masks",
		  "url": "https://www.marketscreener.com/quote/stock/SALESFORCE-COM-INC-12180/news/Business-as-a-Platform-for-Change-Celebrating-Three-Salesforce-Customers-Making-an-Impact-31913383/",
		  "time": "2020-12-08T11:22:24.000+00:00"
		}
	  ]
	}
    ```
*   **Data Not Found:**
    * **Code:** 200 <br />
    * **Content:**
	```json
	{
	  "isin": "US79466L30a24",
	  "newsdata": []
	}
	```
*   **Error Response:**
    *   **Code:** 400 Bad Request <br />
    *   **Content:** `{ "msg" : "Error Occured while retrieving News feeds data from Datalake" }`
    *   **Sample Call:**
      ```javascript
        $.ajax({
          url: "/news/company-news-feeds",
		  requestBody : US79466L3024
          dataType: "json",
          type : "POST",
          success : function(r) {
            console.log(r);
          }
        });
      ```