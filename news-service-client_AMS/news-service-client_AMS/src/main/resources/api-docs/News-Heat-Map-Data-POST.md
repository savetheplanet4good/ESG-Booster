**News Heat Map Data**
----
	Returns json data of news heat map for a specific company isin passed as the input.
*   **URL:**
    /news/company-heat-map
*   **Method:**
    `POST`
*   **Path Params:**
    _None_
*   **Required:**
    `isin=[string]`
*   **Data Params:**
    _None_
*   **Sample:**
    /news/company-heat-map
	* **requestBody:** US79466L3024
*   **Success Response:**
    * **Code:** 200 <br />
    * **Content:** 
    ```json
    {
	  "averageSentimentScore": 0.09,
	  "heatMapData": [
		{
		  "id": "2691ec55-1047-4c3b-8c2f-aa4b86dd945b",
		  "name": "business",
		  "parent": "",
		  "sentimentScore": 0.22,
		  "value": 6620
		},
		{
		  "id": "b54e5bf5-54d2-4f80-8ac3-ffc460f09f45",
		  "name": "society",
		  "parent": "",
		  "sentimentScore": -0.04,
		  "value": 734
		},
		{
		  "id": "47f6d179-041a-4ded-8442-43ba904025fb",
		  "name": "labor-issues",
		  "parent": "2691ec55-1047-4c3b-8c2f-aa4b86dd945b",
		  "sentimentScore": 0.23,
		  "value": 6574
		},
		{
		  "id": "4d56d1d2-9b0a-4617-9114-bd33a1a61c94",
		  "name": "regulatory",
		  "parent": "2691ec55-1047-4c3b-8c2f-aa4b86dd945b",
		  "sentimentScore": -0.23,
		  "value": 40
		},
		{
		  "id": "830bb2d3-403c-4c4f-87e5-7a4018cccb74",
		  "name": "bankruptcy",
		  "parent": "2691ec55-1047-4c3b-8c2f-aa4b86dd945b",
		  "sentimentScore": -0.76,
		  "value": 6
		},
		{
		  "id": "b7311821-fe38-43d7-9890-38acba3585d1",
		  "name": "executive-appointment",
		  "parent": "47f6d179-041a-4ded-8442-43ba904025fb",
		  "sentimentScore": 0.53,
		  "value": 2980
		},
		{
		  "id": "d22d5ed9-89f3-4638-ad3b-192cbde2d61d",
		  "name": "executive-resignation",
		  "parent": "47f6d179-041a-4ded-8442-43ba904025fb",
		  "sentimentScore": -0.57,
		  "value": 1218
		},
		{
		  "id": "856c3f24-a54d-4652-b6ab-f8138f70ba5d",
		  "name": "hirings",
		  "parent": "47f6d179-041a-4ded-8442-43ba904025fb",
		  "sentimentScore": 0.38,
		  "value": 674
		},
		{
		  "id": "99880d61-d979-4635-8e99-830fed16e88e",
		  "name": "layoffs",
		  "parent": "47f6d179-041a-4ded-8442-43ba904025fb",
		  "sentimentScore": -0.51,
		  "value": 592
		},
		{
		  "id": "73d9d7b3-40df-43d9-b5ef-02f0722153b3",
		  "name": "board-member-appointment",
		  "parent": "47f6d179-041a-4ded-8442-43ba904025fb",
		  "sentimentScore": 0.47,
		  "value": 518
		},
		{
		  "id": "d62d0f1c-21e1-40a5-8eca-bf34f63f73d9",
		  "name": "executive-retirement",
		  "parent": "47f6d179-041a-4ded-8442-43ba904025fb",
		  "sentimentScore": -0.49,
		  "value": 320
		},
		{
		  "id": "6800fcdf-e48f-4c1e-bd69-d591cf4a273c",
		  "name": "executive-salary",
		  "parent": "47f6d179-041a-4ded-8442-43ba904025fb",
		  "sentimentScore": 0.24,
		  "value": 96
		},
		{
		  "id": "b5510d08-0a9a-4a31-b98d-179615c0da98",
		  "name": "executive-firing",
		  "parent": "47f6d179-041a-4ded-8442-43ba904025fb",
		  "sentimentScore": -0.6,
		  "value": 76
		},
		{
		  "id": "80c2bca5-75bd-4345-83fc-eff38fbf7070",
		  "name": "executive-search",
		  "parent": "47f6d179-041a-4ded-8442-43ba904025fb",
		  "sentimentScore": 0.49,
		  "value": 38
		},
		{
		  "id": "4c4d6e80-3074-4912-a47b-97d298342c4c",
		  "name": "executive-compensation",
		  "parent": "47f6d179-041a-4ded-8442-43ba904025fb",
		  "sentimentScore": 0,
		  "value": 18
		},
		{
		  "id": "a147b29c-4d85-40ef-b795-20ec7c2faa37",
		  "name": "executive-appointment",
		  "parent": "b7311821-fe38-43d7-9890-38acba3585d1",
		  "sentimentScore": 0.53,
		  "value": 2980
		},
		{
		  "id": "18f2920e-5765-4e72-a0b0-896c19a749c6",
		  "name": "executive-resignation",
		  "parent": "d22d5ed9-89f3-4638-ad3b-192cbde2d61d",
		  "sentimentScore": -0.57,
		  "value": 1218
		},
		{
		  "id": "f8e9fd92-aeeb-4838-8a67-ca028a0d1857",
		  "name": "hirings",
		  "parent": "856c3f24-a54d-4652-b6ab-f8138f70ba5d",
		  "sentimentScore": 0.38,
		  "value": 674
		},
		{
		  "id": "47f8a9c5-8e4f-4993-bf1f-7f41703f765f",
		  "name": "layoffs",
		  "parent": "99880d61-d979-4635-8e99-830fed16e88e",
		  "sentimentScore": -0.51,
		  "value": 592
		},
		{
		  "id": "6e937fe4-9115-4e71-aec1-7f6db73fcc33",
		  "name": "board-member-appointment",
		  "parent": "73d9d7b3-40df-43d9-b5ef-02f0722153b3",
		  "sentimentScore": 0.47,
		  "value": 518
		},
		{
		  "id": "729633e6-5d70-442f-acab-c0864774e109",
		  "name": "executive-retirement",
		  "parent": "d62d0f1c-21e1-40a5-8eca-bf34f63f73d9",
		  "sentimentScore": -0.49,
		  "value": 320
		},
		{
		  "id": "5e355f45-fdf1-4662-a825-e6c30a5224a7",
		  "name": "increase",
		  "parent": "6800fcdf-e48f-4c1e-bd69-d591cf4a273c",
		  "sentimentScore": 0.38,
		  "value": 42
		},
		{
		  "id": "9619784f-08e9-46ea-80ba-37eaa618ed40",
		  "name": "executive-salary",
		  "parent": "6800fcdf-e48f-4c1e-bd69-d591cf4a273c",
		  "sentimentScore": 0.44,
		  "value": 38
		},
		{
		  "id": "e497ff46-878a-4154-8523-70b208092933",
		  "name": "decrease",
		  "parent": "6800fcdf-e48f-4c1e-bd69-d591cf4a273c",
		  "sentimentScore": -0.48,
		  "value": 14
		},
		{
		  "id": "847e5a93-918c-4cc5-a965-70f037b071fe",
		  "name": "unchanged",
		  "parent": "6800fcdf-e48f-4c1e-bd69-d591cf4a273c",
		  "sentimentScore": 0,
		  "value": 2
		},
		{
		  "id": "a56f1947-c7b6-4c7c-b0dd-e070eaab3b1e",
		  "name": "executive-firing",
		  "parent": "b5510d08-0a9a-4a31-b98d-179615c0da98",
		  "sentimentScore": -0.6,
		  "value": 76
		},
		{
		  "id": "08521dec-2a25-473d-8c27-6a9b240ff959",
		  "name": "executive-search",
		  "parent": "80c2bca5-75bd-4345-83fc-eff38fbf7070",
		  "sentimentScore": 0.49,
		  "value": 38
		},
		{
		  "id": "bcc61cc6-6b7b-447c-8c43-902edf07f55a",
		  "name": "executive-compensation",
		  "parent": "4c4d6e80-3074-4912-a47b-97d298342c4c",
		  "sentimentScore": 0,
		  "value": 18
		},
		{
		  "id": "5ba7d6c0-690f-4305-92e8-add274e7102c",
		  "name": "regulatory-investigation",
		  "parent": "4d56d1d2-9b0a-4617-9114-bd33a1a61c94",
		  "sentimentScore": -0.23,
		  "value": 40
		},
		{
		  "id": "df4cc0bb-e270-40e2-8547-c7358f067394",
		  "name": "regulatory-investigation",
		  "parent": "5ba7d6c0-690f-4305-92e8-add274e7102c",
		  "sentimentScore": -0.23,
		  "value": 40
		},
		{
		  "id": "ed95c812-dfc0-4dcc-92c9-4907ae01e031",
		  "name": "bankruptcy",
		  "parent": "830bb2d3-403c-4c4f-87e5-7a4018cccb74",
		  "sentimentScore": -0.76,
		  "value": 6
		},
		{
		  "id": "e0cecd8b-8683-4e27-bc05-adb0e5a65b4f",
		  "name": "bankruptcy",
		  "parent": "ed95c812-dfc0-4dcc-92c9-4907ae01e031",
		  "sentimentScore": -0.76,
		  "value": 6
		},
		{
		  "id": "c1d15a7b-aaba-428a-a1b3-1c7eda39423a",
		  "name": "corporate-responsibility",
		  "parent": "b54e5bf5-54d2-4f80-8ac3-ffc460f09f45",
		  "sentimentScore": 0.26,
		  "value": 356
		},
		{
		  "id": "71db909f-cdba-4f2a-9e44-13c48e851c5e",
		  "name": "legal",
		  "parent": "b54e5bf5-54d2-4f80-8ac3-ffc460f09f45",
		  "sentimentScore": -0.32,
		  "value": 290
		},
		{
		  "id": "a3c20516-5c35-4e1d-aa6a-9d33f459dbc1",
		  "name": "security",
		  "parent": "b54e5bf5-54d2-4f80-8ac3-ffc460f09f45",
		  "sentimentScore": -0.47,
		  "value": 60
		},
		{
		  "id": "4a2060c8-5dff-483e-8c2e-8f5b9df417b4",
		  "name": "civil-unrest",
		  "parent": "b54e5bf5-54d2-4f80-8ac3-ffc460f09f45",
		  "sentimentScore": 0,
		  "value": 14
		},
		{
		  "id": "67fde709-56ef-48ec-a4d6-8fbb0d99b653",
		  "name": "war-conflict",
		  "parent": "b54e5bf5-54d2-4f80-8ac3-ffc460f09f45",
		  "sentimentScore": -0.56,
		  "value": 14
		},
		{
		  "id": "c4cc7097-2734-43d4-9e4b-dbc5b6549413",
		  "name": "donation",
		  "parent": "c1d15a7b-aaba-428a-a1b3-1c7eda39423a",
		  "sentimentScore": 0.26,
		  "value": 352
		},
		{
		  "id": "41b74725-6d11-4851-8158-abfc80d165c0",
		  "name": "sponsorship",
		  "parent": "c1d15a7b-aaba-428a-a1b3-1c7eda39423a",
		  "sentimentScore": 0.31,
		  "value": 4
		},
		{
		  "id": "9d764b5f-ca8d-4b16-bdb0-2144d7f727fe",
		  "name": "donation",
		  "parent": "c4cc7097-2734-43d4-9e4b-dbc5b6549413",
		  "sentimentScore": 0.26,
		  "value": 352
		},
		{
		  "id": "ac4658ec-bfb4-4e01-8d30-c93c803dea91",
		  "name": "sponsorship",
		  "parent": "41b74725-6d11-4851-8158-abfc80d165c0",
		  "sentimentScore": 0.31,
		  "value": 4
		},
		{
		  "id": "738e66e9-384f-4e86-8d61-4ce232abd3cb",
		  "name": "legal-issues",
		  "parent": "71db909f-cdba-4f2a-9e44-13c48e851c5e",
		  "sentimentScore": -0.52,
		  "value": 134
		},
		{
		  "id": "316cbb14-738e-45f1-bdf9-96684689673b",
		  "name": "patent-infringement",
		  "parent": "71db909f-cdba-4f2a-9e44-13c48e851c5e",
		  "sentimentScore": -0.56,
		  "value": 66
		},
		{
		  "id": "2dbe4f0e-5181-4687-a7e0-22f73027472e",
		  "name": "settlement",
		  "parent": "71db909f-cdba-4f2a-9e44-13c48e851c5e",
		  "sentimentScore": 0.46,
		  "value": 56
		},
		{
		  "id": "7320f50a-8f20-4b75-a34a-2ec56d811327",
		  "name": "sanctions",
		  "parent": "71db909f-cdba-4f2a-9e44-13c48e851c5e",
		  "sentimentScore": -0.57,
		  "value": 28
		},
		{
		  "id": "0e47714d-72fb-476e-a60c-80a9757980ab",
		  "name": "verdict",
		  "parent": "71db909f-cdba-4f2a-9e44-13c48e851c5e",
		  "sentimentScore": -0.62,
		  "value": 6
		},
		{
		  "id": "85b3f48e-e6d2-4f69-9e2f-e267318884bf",
		  "name": "legal-issues",
		  "parent": "738e66e9-384f-4e86-8d61-4ce232abd3cb",
		  "sentimentScore": -0.52,
		  "value": 134
		},
		{
		  "id": "94a979fa-61c3-4974-8066-1467ffd97628",
		  "name": "patent-infringement",
		  "parent": "316cbb14-738e-45f1-bdf9-96684689673b",
		  "sentimentScore": -0.56,
		  "value": 66
		},
		{
		  "id": "c25cb436-530a-4bac-a83d-dc6fac6dc3ab",
		  "name": "settlement",
		  "parent": "2dbe4f0e-5181-4687-a7e0-22f73027472e",
		  "sentimentScore": 0.46,
		  "value": 56
		},
		{
		  "id": "e78b4d28-8c48-49f5-83a4-7a7394e8559c",
		  "name": "sanctions",
		  "parent": "7320f50a-8f20-4b75-a34a-2ec56d811327",
		  "sentimentScore": -0.57,
		  "value": 28
		},
		{
		  "id": "6ec09687-c55a-43ca-8547-11145c56cef0",
		  "name": "verdict",
		  "parent": "0e47714d-72fb-476e-a60c-80a9757980ab",
		  "sentimentScore": -0.62,
		  "value": 6
		},
		{
		  "id": "f7db9d5e-3177-4764-99a5-04cbfcb5c774",
		  "name": "cyber-attacks",
		  "parent": "a3c20516-5c35-4e1d-aa6a-9d33f459dbc1",
		  "sentimentScore": -0.58,
		  "value": 42
		},
		{
		  "id": "80554020-a4b3-465c-8089-067fbeb9d82d",
		  "name": "explosion",
		  "parent": "a3c20516-5c35-4e1d-aa6a-9d33f459dbc1",
		  "sentimentScore": -0.65,
		  "value": 16
		},
		{
		  "id": "0c1ed497-3686-434c-bc4a-051479f35f58",
		  "name": "travel-warning",
		  "parent": "a3c20516-5c35-4e1d-aa6a-9d33f459dbc1",
		  "sentimentScore": 0.48,
		  "value": 2
		},
		{
		  "id": "2827c8c2-fa59-4b79-b98c-6176fba322e2",
		  "name": "cyber-attacks",
		  "parent": "f7db9d5e-3177-4764-99a5-04cbfcb5c774",
		  "sentimentScore": -0.58,
		  "value": 42
		},
		{
		  "id": "4f2c338a-2252-4351-a4ae-474b2a13faba",
		  "name": "explosion",
		  "parent": "80554020-a4b3-465c-8089-067fbeb9d82d",
		  "sentimentScore": -0.65,
		  "value": 16
		},
		{
		  "id": "ad610098-0a60-43e8-a44e-be04d77508ff",
		  "name": "lifted",
		  "parent": "0c1ed497-3686-434c-bc4a-051479f35f58",
		  "sentimentScore": 0.48,
		  "value": 2
		},
		{
		  "id": "3ec8225f-42ca-48d6-bb85-14b1d22dafbc",
		  "name": "protest",
		  "parent": "4a2060c8-5dff-483e-8c2e-8f5b9df417b4",
		  "sentimentScore": 0,
		  "value": 14
		},
		{
		  "id": "021ac122-c81b-42f6-a0b3-98eb718b0c07",
		  "name": "protest",
		  "parent": "3ec8225f-42ca-48d6-bb85-14b1d22dafbc",
		  "sentimentScore": 0,
		  "value": 14
		},
		{
		  "id": "dab5e630-44e3-4eb7-a8b1-8006886a05c6",
		  "name": "embargo",
		  "parent": "67fde709-56ef-48ec-a4d6-8fbb0d99b653",
		  "sentimentScore": -0.53,
		  "value": 10
		},
		{
		  "id": "d0f6f3e3-da1c-468d-bff0-f7be1eedb6b6",
		  "name": "violence",
		  "parent": "67fde709-56ef-48ec-a4d6-8fbb0d99b653",
		  "sentimentScore": -0.6,
		  "value": 4
		},
		{
		  "id": "a4f7d218-ce89-4cd7-9674-a2c35c1897cb",
		  "name": "embargo",
		  "parent": "dab5e630-44e3-4eb7-a8b1-8006886a05c6",
		  "sentimentScore": -0.53,
		  "value": 10
		},
		{
		  "id": "a1a899f3-f986-40f3-9e1e-23f135369b04",
		  "name": "attack",
		  "parent": "d0f6f3e3-da1c-468d-bff0-f7be1eedb6b6",
		  "sentimentScore": -0.6,
		  "value": 4
		}
	  ]
	}
    ```
*   **Data Not Found:**
    * **Code:** 200 <br />
    * **Content:**
	```json
	{
	  "averageSentimentScore": null,
	  "heatMapData": null
	}
	```
*   **Error Response:**
    *   **Code:** 400 Bad Request <br />
    *   **Content:** `{ "msg" : "Error Occured while retrieving Heat Map data from Datalake" }`
    *   **Sample Call:**
      ```javascript
        $.ajax({
          url: "/news/company-heat-map",
		  requestBody : US79466L3024
          dataType: "json",
          type : "POST",
          success : function(r) {
            console.log(r);
          }
        });
      ```