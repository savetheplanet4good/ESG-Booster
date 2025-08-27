**Fetch Portfolio Outliers**
----
	Returns CompaniesResponse object which is a list of companies with company details and boolean to denote whether the company is a outlier or not.
*   **URL:**
    /portfolio-mgmt/outliers/{portfolioId}
*   **Method:**
    `GET`
*   **Path Params**
    `{portfolioId} = Portfolio Id`
*   **Required:**
    _{portfolioId}_
*   **Sample:**
    /portfolio-mgmt/outliers/1
*   **Success Response:**
    * **Code:** 200 OK <br />
    * **Content:** 
    ```json
	{
	  "companies": [
		{
		  "isin": "US03027X1000",
		  "name": "American Tower Corporation (REIT)",
		  "wt": 0,
		  "esgScore": 71.67,
		  "environmentalScore": 74.83,
		  "socialScore": 69.83,
		  "governenceScore": 69.71,
		  "holdingValue": 2,
		  "controversyScore": 100,
		  "esgCombinedScore": 71.67,
		  "totalESGCombinedScore": 1.43,
		  "totalESGScore": 1.43,
		  "totalEnvironmentalScore": 1.5,
		  "totalSocialScore": 1.4,
		  "totalGovernanceScore": 1.39,
		  "influenceESGCombinedScore": 0.63,
		  "influenceESGSCore": 0.04,
		  "influenceEnvironmentalScore": 0.06,
		  "influenceSocialScore": -0.16,
		  "influenceGovernanceScore": 0.24,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 87.21
			},
			{
			  "name": "Resource Use",
			  "score": 81.93
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 68.08
			},
			{
			  "name": "Product Responsibility",
			  "score": 87.46
			},
			{
			  "name": "WorkForce",
			  "score": 74.01
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 56.96
			},
			{
			  "name": "Management Score",
			  "score": 74.21
			},
			{
			  "name": "Shareholders",
			  "score": 63.22
			}
		  ],
		  "news": null,
		  "outlierScore": 28,
		  "refinitivNormalisedScore": 0.596,
		  "sustainalyticsNormalisedScore": 0.32,
		  "refinitivESGCombinedScore": 71.671,
		  "sustainalyticsESGCombinedScore": 18,
		  "isOutLier": true,
		  "isinType": null
		},
		{
		  "isin": "US7181721090",
		  "name": "Philip Morris International Inc.",
		  "wt": 0,
		  "esgScore": 87.31,
		  "environmentalScore": 93.58,
		  "socialScore": 87.01,
		  "governenceScore": 80.91,
		  "holdingValue": 4,
		  "controversyScore": 11.29,
		  "esgCombinedScore": 49.3,
		  "totalESGCombinedScore": 1.97,
		  "totalESGScore": 3.49,
		  "totalEnvironmentalScore": 3.74,
		  "totalSocialScore": 3.48,
		  "totalGovernanceScore": 3.24,
		  "influenceESGCombinedScore": -0.38,
		  "influenceESGSCore": 0.98,
		  "influenceEnvironmentalScore": 1.16,
		  "influenceSocialScore": 0.59,
		  "influenceGovernanceScore": 1.19,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 95.75
			},
			{
			  "name": "Environmental Innovation",
			  "score": 69.34
			},
			{
			  "name": "Resource Use",
			  "score": 96.27
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 91.35
			},
			{
			  "name": "Human Rights",
			  "score": 93.29
			},
			{
			  "name": "Product Responsibility",
			  "score": 69.41
			},
			{
			  "name": "WorkForce",
			  "score": 98.1
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 83.48
			},
			{
			  "name": "Management Score",
			  "score": 81.05
			},
			{
			  "name": "Shareholders",
			  "score": 78.75
			}
		  ],
		  "news": null,
		  "outlierScore": 27,
		  "refinitivNormalisedScore": 0.341,
		  "sustainalyticsNormalisedScore": 0.613,
		  "refinitivESGCombinedScore": 49.299,
		  "sustainalyticsESGCombinedScore": 25,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US68389X1054",
		  "name": "Oracle Corporation",
		  "wt": 0,
		  "esgScore": 52.32,
		  "environmentalScore": 78.52,
		  "socialScore": 75.83,
		  "governenceScore": 24.22,
		  "holdingValue": 7,
		  "controversyScore": 25.64,
		  "esgCombinedScore": 38.98,
		  "totalESGCombinedScore": 2.73,
		  "totalESGScore": 3.66,
		  "totalEnvironmentalScore": 5.5,
		  "totalSocialScore": 5.31,
		  "totalGovernanceScore": 1.7,
		  "influenceESGCombinedScore": -1.99,
		  "influenceESGSCore": -1.78,
		  "influenceEnvironmentalScore": 0.58,
		  "influenceSocialScore": -0.01,
		  "influenceGovernanceScore": -4.28,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 97.22
			},
			{
			  "name": "Environmental Innovation",
			  "score": 53.57
			},
			{
			  "name": "Resource Use",
			  "score": 99.3
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 97.02
			},
			{
			  "name": "Human Rights",
			  "score": 89.74
			},
			{
			  "name": "Product Responsibility",
			  "score": 35.73
			},
			{
			  "name": "WorkForce",
			  "score": 81.74
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 77.62
			},
			{
			  "name": "Management Score",
			  "score": 11.35
			},
			{
			  "name": "Shareholders",
			  "score": 31.53
			}
		  ],
		  "news": null,
		  "outlierScore": 14,
		  "refinitivNormalisedScore": 0.358,
		  "sustainalyticsNormalisedScore": 0.5,
		  "refinitivESGCombinedScore": 38.982,
		  "sustainalyticsESGCombinedScore": 18,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US1667641005",
		  "name": "Chevron Corporation",
		  "wt": 0,
		  "esgScore": 87.75,
		  "environmentalScore": 83.12,
		  "socialScore": 90.21,
		  "governenceScore": 90.16,
		  "holdingValue": 1,
		  "controversyScore": 33.96,
		  "esgCombinedScore": 60.86,
		  "totalESGCombinedScore": 0.61,
		  "totalESGScore": 0.88,
		  "totalEnvironmentalScore": 0.83,
		  "totalSocialScore": 0.9,
		  "totalGovernanceScore": 0.9,
		  "influenceESGCombinedScore": 0.12,
		  "influenceESGSCore": 0.25,
		  "influenceEnvironmentalScore": 0.15,
		  "influenceSocialScore": 0.19,
		  "influenceGovernanceScore": 0.45,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 88.81
			},
			{
			  "name": "Environmental Innovation",
			  "score": 81.15
			},
			{
			  "name": "Resource Use",
			  "score": 79.95
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 96.94
			},
			{
			  "name": "Human Rights",
			  "score": 95.35
			},
			{
			  "name": "Product Responsibility",
			  "score": 79.61
			},
			{
			  "name": "WorkForce",
			  "score": 84.69
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 63.19
			},
			{
			  "name": "Management Score",
			  "score": 96.61
			},
			{
			  "name": "Shareholders",
			  "score": 86.63
			}
		  ],
		  "news": null,
		  "outlierScore": 23,
		  "refinitivNormalisedScore": 0.637,
		  "sustainalyticsNormalisedScore": 0.41,
		  "refinitivESGCombinedScore": 60.856,
		  "sustainalyticsESGCombinedScore": 40,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US9078181081",
		  "name": "Union Pacific Corporation",
		  "wt": 0,
		  "esgScore": 58.42,
		  "environmentalScore": 47,
		  "socialScore": 72.52,
		  "governenceScore": 53.8,
		  "holdingValue": 6,
		  "controversyScore": 87.5,
		  "esgCombinedScore": 58.42,
		  "totalESGCombinedScore": 3.51,
		  "totalESGScore": 3.51,
		  "totalEnvironmentalScore": 2.82,
		  "totalSocialScore": 4.35,
		  "totalGovernanceScore": 3.23,
		  "influenceESGCombinedScore": 0.44,
		  "influenceESGSCore": -1.01,
		  "influenceEnvironmentalScore": -2.11,
		  "influenceSocialScore": -0.27,
		  "influenceGovernanceScore": -0.82,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 86.57
			},
			{
			  "name": "Resource Use",
			  "score": 51.5
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 97.93
			},
			{
			  "name": "Human Rights",
			  "score": 11.33
			},
			{
			  "name": "Product Responsibility",
			  "score": 72.73
			},
			{
			  "name": "WorkForce",
			  "score": 90.5
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 89.85
			},
			{
			  "name": "Management Score",
			  "score": 42.22
			},
			{
			  "name": "Shareholders",
			  "score": 68.4
			}
		  ],
		  "news": null,
		  "outlierScore": 9,
		  "refinitivNormalisedScore": 0.498,
		  "sustainalyticsNormalisedScore": 0.408,
		  "refinitivESGCombinedScore": 58.422,
		  "sustainalyticsESGCombinedScore": 22,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US65339F1012",
		  "name": "NextEra Energy, Inc.",
		  "wt": 0,
		  "esgScore": 65.67,
		  "environmentalScore": 76.5,
		  "socialScore": 56.96,
		  "governenceScore": 58.57,
		  "holdingValue": 5,
		  "controversyScore": 100,
		  "esgCombinedScore": 65.67,
		  "totalESGCombinedScore": 3.28,
		  "totalESGScore": 3.28,
		  "totalEnvironmentalScore": 3.83,
		  "totalSocialScore": 2.85,
		  "totalGovernanceScore": 2.93,
		  "influenceESGCombinedScore": 1.03,
		  "influenceESGSCore": -0.32,
		  "influenceEnvironmentalScore": 0.27,
		  "influenceSocialScore": -1.25,
		  "influenceGovernanceScore": -0.3,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 81.58
			},
			{
			  "name": "Environmental Innovation",
			  "score": 82.03
			},
			{
			  "name": "Resource Use",
			  "score": 65.96
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 74.31
			},
			{
			  "name": "Human Rights",
			  "score": 55.51
			},
			{
			  "name": "Product Responsibility",
			  "score": 52.88
			},
			{
			  "name": "WorkForce",
			  "score": 47.79
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 67.83
			},
			{
			  "name": "Management Score",
			  "score": 71.67
			},
			{
			  "name": "Shareholders",
			  "score": 8.74
			}
		  ],
		  "news": null,
		  "outlierScore": 5,
		  "refinitivNormalisedScore": 0.571,
		  "sustainalyticsNormalisedScore": 0.523,
		  "refinitivESGCombinedScore": 65.668,
		  "sustainalyticsESGCombinedScore": 29,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US8552441094",
		  "name": "Starbucks Corporation",
		  "wt": 0,
		  "esgScore": 58.47,
		  "environmentalScore": 62.34,
		  "socialScore": 66.48,
		  "governenceScore": 42.98,
		  "holdingValue": 5,
		  "controversyScore": 16.67,
		  "esgCombinedScore": 37.57,
		  "totalESGCombinedScore": 1.88,
		  "totalESGScore": 2.92,
		  "totalEnvironmentalScore": 3.12,
		  "totalSocialScore": 3.32,
		  "totalGovernanceScore": 2.15,
		  "influenceESGCombinedScore": -1.55,
		  "influenceESGSCore": -0.83,
		  "influenceEnvironmentalScore": -0.7,
		  "influenceSocialScore": -0.62,
		  "influenceGovernanceScore": -1.55,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 45.17
			},
			{
			  "name": "Environmental Innovation",
			  "score": 93.1
			},
			{
			  "name": "Resource Use",
			  "score": 73.46
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 67.77
			},
			{
			  "name": "Human Rights",
			  "score": 72.43
			},
			{
			  "name": "Product Responsibility",
			  "score": 66.02
			},
			{
			  "name": "WorkForce",
			  "score": 60.66
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 91.8
			},
			{
			  "name": "Management Score",
			  "score": 28.96
			},
			{
			  "name": "Shareholders",
			  "score": 57.16
			}
		  ],
		  "news": null,
		  "outlierScore": 26,
		  "refinitivNormalisedScore": 0.346,
		  "sustainalyticsNormalisedScore": 0.603,
		  "refinitivESGCombinedScore": 37.569,
		  "sustainalyticsESGCombinedScore": 21,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US2358511028",
		  "name": "Danaher Corporation",
		  "wt": 0,
		  "esgScore": 70.86,
		  "environmentalScore": 50.25,
		  "socialScore": 76.99,
		  "governenceScore": 72.42,
		  "holdingValue": 3,
		  "controversyScore": 100,
		  "esgCombinedScore": 70.86,
		  "totalESGCombinedScore": 2.13,
		  "totalESGScore": 2.13,
		  "totalEnvironmentalScore": 1.51,
		  "totalSocialScore": 2.31,
		  "totalGovernanceScore": 2.17,
		  "influenceESGCombinedScore": 0.9,
		  "influenceESGSCore": 0.03,
		  "influenceEnvironmentalScore": -0.92,
		  "influenceSocialScore": 0.04,
		  "influenceGovernanceScore": 0.49,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 35.54
			},
			{
			  "name": "Environmental Innovation",
			  "score": 50
			},
			{
			  "name": "Resource Use",
			  "score": 68.95
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 87.21
			},
			{
			  "name": "Human Rights",
			  "score": 75
			},
			{
			  "name": "Product Responsibility",
			  "score": 75.9
			},
			{
			  "name": "WorkForce",
			  "score": 67.73
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 59.63
			},
			{
			  "name": "Management Score",
			  "score": 90.79
			},
			{
			  "name": "Shareholders",
			  "score": 19.73
			}
		  ],
		  "news": null,
		  "outlierScore": 43,
		  "refinitivNormalisedScore": 0.681,
		  "sustainalyticsNormalisedScore": 0.25,
		  "refinitivESGCombinedScore": 70.864,
		  "sustainalyticsESGCombinedScore": 33,
		  "isOutLier": true,
		  "isinType": null
		},
		{
		  "isin": "US0311621009",
		  "name": "Amgen Inc.",
		  "wt": 0,
		  "esgScore": 70.78,
		  "environmentalScore": 76.59,
		  "socialScore": 81.04,
		  "governenceScore": 51.16,
		  "holdingValue": 2,
		  "controversyScore": 18.48,
		  "esgCombinedScore": 44.63,
		  "totalESGCombinedScore": 0.89,
		  "totalESGScore": 1.42,
		  "totalEnvironmentalScore": 1.53,
		  "totalSocialScore": 1.62,
		  "totalGovernanceScore": 1.02,
		  "influenceESGCombinedScore": -0.36,
		  "influenceESGSCore": 0.02,
		  "influenceEnvironmentalScore": 0.11,
		  "influenceSocialScore": 0.14,
		  "influenceGovernanceScore": -0.36,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 78.97
			},
			{
			  "name": "Environmental Innovation",
			  "score": 8
			},
			{
			  "name": "Resource Use",
			  "score": 95.04
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 88.64
			},
			{
			  "name": "Human Rights",
			  "score": 74.18
			},
			{
			  "name": "Product Responsibility",
			  "score": 86.29
			},
			{
			  "name": "WorkForce",
			  "score": 78.53
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 94.97
			},
			{
			  "name": "Management Score",
			  "score": 33.22
			},
			{
			  "name": "Shareholders",
			  "score": 81.76
			}
		  ],
		  "news": null,
		  "outlierScore": 37,
		  "refinitivNormalisedScore": 0.378,
		  "sustainalyticsNormalisedScore": 0.75,
		  "refinitivESGCombinedScore": 44.627,
		  "sustainalyticsESGCombinedScore": 20,
		  "isOutLier": true,
		  "isinType": null
		},
		{
		  "isin": "US9497461015",
		  "name": "Wells Fargo & Company",
		  "wt": 0,
		  "esgScore": 80.21,
		  "environmentalScore": 86.82,
		  "socialScore": 75.18,
		  "governenceScore": 84.5,
		  "holdingValue": 4,
		  "controversyScore": 0.48,
		  "esgCombinedScore": 40.35,
		  "totalESGCombinedScore": 1.61,
		  "totalESGScore": 3.21,
		  "totalEnvironmentalScore": 3.47,
		  "totalSocialScore": 3.01,
		  "totalGovernanceScore": 3.38,
		  "influenceESGCombinedScore": -1.04,
		  "influenceESGSCore": 0.57,
		  "influenceEnvironmentalScore": 0.79,
		  "influenceSocialScore": -0.04,
		  "influenceGovernanceScore": 1.42,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 82.34
			},
			{
			  "name": "Environmental Innovation",
			  "score": 87.35
			},
			{
			  "name": "Resource Use",
			  "score": 89.16
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 97.27
			},
			{
			  "name": "Human Rights",
			  "score": 47.41
			},
			{
			  "name": "Product Responsibility",
			  "score": 74.57
			},
			{
			  "name": "WorkForce",
			  "score": 75.55
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 83.48
			},
			{
			  "name": "Management Score",
			  "score": 91.57
			},
			{
			  "name": "Shareholders",
			  "score": 61.62
			}
		  ],
		  "news": null,
		  "outlierScore": 1,
		  "refinitivNormalisedScore": 0.338,
		  "sustainalyticsNormalisedScore": 0.33,
		  "refinitivESGCombinedScore": 40.349,
		  "sustainalyticsESGCombinedScore": 31,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US4385161066",
		  "name": "Honeywell International Inc.",
		  "wt": 0,
		  "esgScore": 69.34,
		  "environmentalScore": 67.5,
		  "socialScore": 60.93,
		  "governenceScore": 85.71,
		  "holdingValue": 2,
		  "controversyScore": 53.12,
		  "esgCombinedScore": 61.23,
		  "totalESGCombinedScore": 1.22,
		  "totalESGScore": 1.39,
		  "totalEnvironmentalScore": 1.35,
		  "totalSocialScore": 1.22,
		  "totalGovernanceScore": 1.71,
		  "influenceESGCombinedScore": 0.25,
		  "influenceESGSCore": -0.02,
		  "influenceEnvironmentalScore": -0.14,
		  "influenceSocialScore": -0.39,
		  "influenceGovernanceScore": 0.75,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 54.44
			},
			{
			  "name": "Environmental Innovation",
			  "score": 82.86
			},
			{
			  "name": "Resource Use",
			  "score": 59.3
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 78.12
			},
			{
			  "name": "Human Rights",
			  "score": 63.23
			},
			{
			  "name": "Product Responsibility",
			  "score": 57.95
			},
			{
			  "name": "WorkForce",
			  "score": 44.79
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 38.7
			},
			{
			  "name": "Management Score",
			  "score": 91.1
			},
			{
			  "name": "Shareholders",
			  "score": 99.08
			}
		  ],
		  "news": null,
		  "outlierScore": 20,
		  "refinitivNormalisedScore": 0.729,
		  "sustainalyticsNormalisedScore": 0.528,
		  "refinitivESGCombinedScore": 61.231,
		  "sustainalyticsESGCombinedScore": 33,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US79466L3024",
		  "name": "salesforce.com, inc.",
		  "wt": 0,
		  "esgScore": 60.39,
		  "environmentalScore": 72.79,
		  "socialScore": 73.33,
		  "governenceScore": 45.54,
		  "holdingValue": 4,
		  "controversyScore": 90.39,
		  "esgCombinedScore": 60.39,
		  "totalESGCombinedScore": 2.42,
		  "totalESGScore": 2.42,
		  "totalEnvironmentalScore": 2.91,
		  "totalSocialScore": 2.93,
		  "totalGovernanceScore": 1.82,
		  "influenceESGCombinedScore": 0.44,
		  "influenceESGSCore": -0.56,
		  "influenceEnvironmentalScore": 0.01,
		  "influenceSocialScore": -0.14,
		  "influenceGovernanceScore": -1.08,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 77.02
			},
			{
			  "name": "Environmental Innovation",
			  "score": 53.57
			},
			{
			  "name": "Resource Use",
			  "score": 95.58
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 94.87
			},
			{
			  "name": "Human Rights",
			  "score": 63.08
			},
			{
			  "name": "Product Responsibility",
			  "score": 35.73
			},
			{
			  "name": "WorkForce",
			  "score": 95.82
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 63.46
			},
			{
			  "name": "Management Score",
			  "score": 43.01
			},
			{
			  "name": "Shareholders",
			  "score": 42.03
			}
		  ],
		  "news": null,
		  "outlierScore": 15,
		  "refinitivNormalisedScore": 0.603,
		  "sustainalyticsNormalisedScore": 0.75,
		  "refinitivESGCombinedScore": 60.392,
		  "sustainalyticsESGCombinedScore": 11,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "IE00BTN1Y115",
		  "name": "Medtronic plc",
		  "wt": 0,
		  "esgScore": 77.59,
		  "environmentalScore": 78.28,
		  "socialScore": 88.8,
		  "governenceScore": 62.52,
		  "holdingValue": 5,
		  "controversyScore": 24.29,
		  "esgCombinedScore": 50.94,
		  "totalESGCombinedScore": 2.55,
		  "totalESGScore": 3.88,
		  "totalEnvironmentalScore": 3.91,
		  "totalSocialScore": 4.44,
		  "totalGovernanceScore": 3.13,
		  "influenceESGCombinedScore": -0.32,
		  "influenceESGSCore": 0.53,
		  "influenceEnvironmentalScore": 0.4,
		  "influenceSocialScore": 0.85,
		  "influenceGovernanceScore": 0.02,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 90.31
			},
			{
			  "name": "Environmental Innovation",
			  "score": 50
			},
			{
			  "name": "Resource Use",
			  "score": 98.6
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 99.72
			},
			{
			  "name": "Human Rights",
			  "score": 94.55
			},
			{
			  "name": "Product Responsibility",
			  "score": 71.22
			},
			{
			  "name": "WorkForce",
			  "score": 94.69
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 60.26
			},
			{
			  "name": "Management Score",
			  "score": 64.29
			},
			{
			  "name": "Shareholders",
			  "score": 58.16
			}
		  ],
		  "news": null,
		  "outlierScore": 7,
		  "refinitivNormalisedScore": 0.414,
		  "sustainalyticsNormalisedScore": 0.482,
		  "refinitivESGCombinedScore": 50.94,
		  "sustainalyticsESGCombinedScore": 25,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US0028241000",
		  "name": "Abbott Laboratories",
		  "wt": 0,
		  "esgScore": 76.89,
		  "environmentalScore": 74.81,
		  "socialScore": 79.78,
		  "governenceScore": 74.04,
		  "holdingValue": 3,
		  "controversyScore": 30,
		  "esgCombinedScore": 53.44,
		  "totalESGCombinedScore": 1.6,
		  "totalESGScore": 2.31,
		  "totalEnvironmentalScore": 2.24,
		  "totalSocialScore": 2.39,
		  "totalGovernanceScore": 2.22,
		  "influenceESGCombinedScore": -0.06,
		  "influenceESGSCore": 0.29,
		  "influenceEnvironmentalScore": 0.09,
		  "influenceSocialScore": 0.15,
		  "influenceGovernanceScore": 0.57,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 93.37
			},
			{
			  "name": "Environmental Innovation",
			  "score": 50
			},
			{
			  "name": "Resource Use",
			  "score": 82.63
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 97.38
			},
			{
			  "name": "Human Rights",
			  "score": 54.08
			},
			{
			  "name": "Product Responsibility",
			  "score": 75.9
			},
			{
			  "name": "WorkForce",
			  "score": 96.8
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 83.48
			},
			{
			  "name": "Management Score",
			  "score": 68.58
			},
			{
			  "name": "Shareholders",
			  "score": 85.98
			}
		  ],
		  "news": null,
		  "outlierScore": 12,
		  "refinitivNormalisedScore": 0.448,
		  "sustainalyticsNormalisedScore": 0.332,
		  "refinitivESGCombinedScore": 53.444,
		  "sustainalyticsESGCombinedScore": 30,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US09247X1019",
		  "name": "BlackRock, Inc.",
		  "wt": 0,
		  "esgScore": 70.11,
		  "environmentalScore": 61.75,
		  "socialScore": 77.94,
		  "governenceScore": 66,
		  "holdingValue": 2,
		  "controversyScore": 18.97,
		  "esgCombinedScore": 44.54,
		  "totalESGCombinedScore": 0.89,
		  "totalESGScore": 1.4,
		  "totalEnvironmentalScore": 1.24,
		  "totalSocialScore": 1.56,
		  "totalGovernanceScore": 1.32,
		  "influenceESGCombinedScore": -0.36,
		  "influenceESGSCore": 0,
		  "influenceEnvironmentalScore": -0.3,
		  "influenceSocialScore": 0.05,
		  "influenceGovernanceScore": 0.12,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 81.67
			},
			{
			  "name": "Environmental Innovation",
			  "score": 48.99
			},
			{
			  "name": "Resource Use",
			  "score": 73.74
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 76.14
			},
			{
			  "name": "Human Rights",
			  "score": 86.11
			},
			{
			  "name": "Product Responsibility",
			  "score": 45.48
			},
			{
			  "name": "WorkForce",
			  "score": 99.79
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 62.42
			},
			{
			  "name": "Management Score",
			  "score": 67.29
			},
			{
			  "name": "Shareholders",
			  "score": 64.12
			}
		  ],
		  "news": null,
		  "outlierScore": 16,
		  "refinitivNormalisedScore": 0.381,
		  "sustainalyticsNormalisedScore": 0.542,
		  "refinitivESGCombinedScore": 44.538,
		  "sustainalyticsESGCombinedScore": 23,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "IE00B4BNMY34",
		  "name": "Accenture plc",
		  "wt": 0,
		  "esgScore": 72.32,
		  "environmentalScore": 77.68,
		  "socialScore": 78.73,
		  "governenceScore": 65.2,
		  "holdingValue": 9,
		  "controversyScore": 100,
		  "esgCombinedScore": 72.32,
		  "totalESGCombinedScore": 6.51,
		  "totalESGScore": 6.51,
		  "totalEnvironmentalScore": 6.99,
		  "totalSocialScore": 7.09,
		  "totalGovernanceScore": 5.87,
		  "influenceESGCombinedScore": 2.95,
		  "influenceESGSCore": 0.27,
		  "influenceEnvironmentalScore": 0.64,
		  "influenceSocialScore": 0.34,
		  "influenceGovernanceScore": 0.42,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 99.75
			},
			{
			  "name": "Environmental Innovation",
			  "score": 53.57
			},
			{
			  "name": "Resource Use",
			  "score": 95.12
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 97.97
			},
			{
			  "name": "Human Rights",
			  "score": 85.64
			},
			{
			  "name": "Product Responsibility",
			  "score": 35.73
			},
			{
			  "name": "WorkForce",
			  "score": 99.88
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 93.59
			},
			{
			  "name": "Management Score",
			  "score": 62.24
			},
			{
			  "name": "Shareholders",
			  "score": 56.12
			}
		  ],
		  "news": null,
		  "outlierScore": 1,
		  "refinitivNormalisedScore": 0.74,
		  "sustainalyticsNormalisedScore": 0.75,
		  "refinitivESGCombinedScore": 72.322,
		  "sustainalyticsESGCombinedScore": 11,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US8825081040",
		  "name": "Texas Instruments Incorporated",
		  "wt": 0,
		  "esgScore": 85.06,
		  "environmentalScore": 96.83,
		  "socialScore": 88.69,
		  "governenceScore": 64.95,
		  "holdingValue": 1,
		  "controversyScore": 100,
		  "esgCombinedScore": 85.06,
		  "totalESGCombinedScore": 0.85,
		  "totalESGScore": 0.85,
		  "totalEnvironmentalScore": 0.97,
		  "totalSocialScore": 0.89,
		  "totalGovernanceScore": 0.65,
		  "influenceESGCombinedScore": 0.56,
		  "influenceESGSCore": 0.21,
		  "influenceEnvironmentalScore": 0.34,
		  "influenceSocialScore": 0.17,
		  "influenceGovernanceScore": 0.04,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 95.83
			},
			{
			  "name": "Environmental Innovation",
			  "score": 95.45
			},
			{
			  "name": "Resource Use",
			  "score": 99.58
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 96.69
			},
			{
			  "name": "Human Rights",
			  "score": 77.19
			},
			{
			  "name": "Product Responsibility",
			  "score": 93.89
			},
			{
			  "name": "WorkForce",
			  "score": 95.22
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 58.82
			},
			{
			  "name": "Management Score",
			  "score": 75.66
			},
			{
			  "name": "Shareholders",
			  "score": 33.38
			}
		  ],
		  "news": null,
		  "outlierScore": 12,
		  "refinitivNormalisedScore": 0.75,
		  "sustainalyticsNormalisedScore": 0.627,
		  "refinitivESGCombinedScore": 85.064,
		  "sustainalyticsESGCombinedScore": 17,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US1101221083",
		  "name": "Bristol-Myers Squibb Company",
		  "wt": 0,
		  "esgScore": 75.28,
		  "environmentalScore": 72.85,
		  "socialScore": 79.66,
		  "governenceScore": 70.53,
		  "holdingValue": 7,
		  "controversyScore": 52.17,
		  "esgCombinedScore": 63.73,
		  "totalESGCombinedScore": 4.46,
		  "totalESGScore": 5.27,
		  "totalEnvironmentalScore": 5.1,
		  "totalSocialScore": 5.58,
		  "totalGovernanceScore": 4.94,
		  "influenceESGCombinedScore": 1.19,
		  "influenceESGSCore": 0.51,
		  "influenceEnvironmentalScore": 0.03,
		  "influenceSocialScore": 0.35,
		  "influenceGovernanceScore": 0.92,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 86.91
			},
			{
			  "name": "Resource Use",
			  "score": 82.06
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 91.67
			},
			{
			  "name": "Human Rights",
			  "score": 82.79
			},
			{
			  "name": "Product Responsibility",
			  "score": 55.38
			},
			{
			  "name": "WorkForce",
			  "score": 85.1
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 92.42
			},
			{
			  "name": "Management Score",
			  "score": 60.79
			},
			{
			  "name": "Shareholders",
			  "score": 88.36
			}
		  ],
		  "news": null,
		  "outlierScore": 6,
		  "refinitivNormalisedScore": 0.581,
		  "sustainalyticsNormalisedScore": 0.519,
		  "refinitivESGCombinedScore": 63.727,
		  "sustainalyticsESGCombinedScore": 28,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US0970231058",
		  "name": "The Boeing Company",
		  "wt": 0,
		  "esgScore": 79.52,
		  "environmentalScore": 75.9,
		  "socialScore": 79.2,
		  "governenceScore": 83.69,
		  "holdingValue": 1,
		  "controversyScore": 2.5,
		  "esgCombinedScore": 41.01,
		  "totalESGCombinedScore": 0.41,
		  "totalESGScore": 0.8,
		  "totalEnvironmentalScore": 0.76,
		  "totalSocialScore": 0.79,
		  "totalGovernanceScore": 0.84,
		  "influenceESGCombinedScore": -0.25,
		  "influenceESGSCore": 0.13,
		  "influenceEnvironmentalScore": 0.05,
		  "influenceSocialScore": 0.04,
		  "influenceGovernanceScore": 0.34,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 81.37
			},
			{
			  "name": "Environmental Innovation",
			  "score": 80
			},
			{
			  "name": "Resource Use",
			  "score": 57
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 83.09
			},
			{
			  "name": "Human Rights",
			  "score": 94.79
			},
			{
			  "name": "Product Responsibility",
			  "score": 41.41
			},
			{
			  "name": "WorkForce",
			  "score": 78.68
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 38.7
			},
			{
			  "name": "Management Score",
			  "score": 94.35
			},
			{
			  "name": "Shareholders",
			  "score": 78.16
			}
		  ],
		  "news": null,
		  "outlierScore": 4,
		  "refinitivNormalisedScore": 0.292,
		  "sustainalyticsNormalisedScore": 0.25,
		  "refinitivESGCombinedScore": 41.01,
		  "sustainalyticsESGCombinedScore": 39,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US1729674242",
		  "name": "Citigroup Inc.",
		  "wt": 0,
		  "esgScore": 79.31,
		  "environmentalScore": 64.92,
		  "socialScore": 83.3,
		  "governenceScore": 79.57,
		  "holdingValue": 1,
		  "controversyScore": 5.65,
		  "esgCombinedScore": 42.48,
		  "totalESGCombinedScore": 0.42,
		  "totalESGScore": 0.79,
		  "totalEnvironmentalScore": 0.65,
		  "totalSocialScore": 0.83,
		  "totalGovernanceScore": 0.8,
		  "influenceESGCombinedScore": -0.22,
		  "influenceESGSCore": 0.13,
		  "influenceEnvironmentalScore": -0.1,
		  "influenceSocialScore": 0.1,
		  "influenceGovernanceScore": 0.28,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 91.12
			},
			{
			  "name": "Environmental Innovation",
			  "score": 51.06
			},
			{
			  "name": "Resource Use",
			  "score": 94.14
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 83.5
			},
			{
			  "name": "Human Rights",
			  "score": 80
			},
			{
			  "name": "Product Responsibility",
			  "score": 75.27
			},
			{
			  "name": "WorkForce",
			  "score": 88.5
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 85.15
			},
			{
			  "name": "Management Score",
			  "score": 87.9
			},
			{
			  "name": "Shareholders",
			  "score": 48.09
			}
		  ],
		  "news": null,
		  "outlierScore": 15,
		  "refinitivNormalisedScore": 0.366,
		  "sustainalyticsNormalisedScore": 0.514,
		  "refinitivESGCombinedScore": 42.481,
		  "sustainalyticsESGCombinedScore": 26,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US7475251036",
		  "name": "QUALCOMM Incorporated",
		  "wt": 0,
		  "esgScore": 70.59,
		  "environmentalScore": 64.5,
		  "socialScore": 76.88,
		  "governenceScore": 67.53,
		  "holdingValue": 10,
		  "controversyScore": 2.08,
		  "esgCombinedScore": 36.34,
		  "totalESGCombinedScore": 3.63,
		  "totalESGScore": 7.06,
		  "totalEnvironmentalScore": 6.45,
		  "totalSocialScore": 7.69,
		  "totalGovernanceScore": 6.75,
		  "influenceESGCombinedScore": -3.33,
		  "influenceESGSCore": 0.06,
		  "influenceEnvironmentalScore": -1.11,
		  "influenceSocialScore": 0.13,
		  "influenceGovernanceScore": 0.84,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 98.76
			},
			{
			  "name": "Environmental Innovation",
			  "score": 33.68
			},
			{
			  "name": "Resource Use",
			  "score": 69.44
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 76.69
			},
			{
			  "name": "Human Rights",
			  "score": 92.56
			},
			{
			  "name": "Product Responsibility",
			  "score": 52.17
			},
			{
			  "name": "WorkForce",
			  "score": 73.99
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 97.11
			},
			{
			  "name": "Management Score",
			  "score": 58.53
			},
			{
			  "name": "Shareholders",
			  "score": 77.78
			}
		  ],
		  "news": null,
		  "outlierScore": 16,
		  "refinitivNormalisedScore": 0.25,
		  "sustainalyticsNormalisedScore": 0.41,
		  "refinitivESGCombinedScore": 36.337,
		  "sustainalyticsESGCombinedScore": 26,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US00287Y1091",
		  "name": "AbbVie Inc.",
		  "wt": 0,
		  "esgScore": 77.86,
		  "environmentalScore": 71.11,
		  "socialScore": 91.15,
		  "governenceScore": 62.95,
		  "holdingValue": 5,
		  "controversyScore": 30.43,
		  "esgCombinedScore": 54.15,
		  "totalESGCombinedScore": 2.71,
		  "totalESGScore": 3.89,
		  "totalEnvironmentalScore": 3.56,
		  "totalSocialScore": 4.56,
		  "totalGovernanceScore": 3.15,
		  "influenceESGCombinedScore": -0.03,
		  "influenceESGSCore": 0.55,
		  "influenceEnvironmentalScore": -0.1,
		  "influenceSocialScore": 1.01,
		  "influenceGovernanceScore": 0.05,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 76.59
			},
			{
			  "name": "Environmental Innovation",
			  "score": 8
			},
			{
			  "name": "Resource Use",
			  "score": 85.11
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 91.16
			},
			{
			  "name": "Human Rights",
			  "score": 87.7
			},
			{
			  "name": "Product Responsibility",
			  "score": 90.05
			},
			{
			  "name": "WorkForce",
			  "score": 96.21
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 73.79
			},
			{
			  "name": "Management Score",
			  "score": 52.35
			},
			{
			  "name": "Shareholders",
			  "score": 91.06
			}
		  ],
		  "news": null,
		  "outlierScore": 6,
		  "refinitivNormalisedScore": 0.48,
		  "sustainalyticsNormalisedScore": 0.423,
		  "refinitivESGCombinedScore": 54.147,
		  "sustainalyticsESGCombinedScore": 31,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US22160K1051",
		  "name": "Costco Wholesale Corporation",
		  "wt": 0,
		  "esgScore": 64.04,
		  "environmentalScore": 76.28,
		  "socialScore": 46.57,
		  "governenceScore": 71.31,
		  "holdingValue": 6,
		  "controversyScore": 89.29,
		  "esgCombinedScore": 64.04,
		  "totalESGCombinedScore": 3.84,
		  "totalESGScore": 3.84,
		  "totalEnvironmentalScore": 4.58,
		  "totalSocialScore": 2.79,
		  "totalGovernanceScore": 4.28,
		  "influenceESGCombinedScore": 1.06,
		  "influenceESGSCore": -0.53,
		  "influenceEnvironmentalScore": 0.31,
		  "influenceSocialScore": -2.32,
		  "influenceGovernanceScore": 0.87,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 85.85
			},
			{
			  "name": "Environmental Innovation",
			  "score": 17.24
			},
			{
			  "name": "Resource Use",
			  "score": 82.26
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 36.18
			},
			{
			  "name": "Human Rights",
			  "score": 50
			},
			{
			  "name": "Product Responsibility",
			  "score": 60.48
			},
			{
			  "name": "WorkForce",
			  "score": 42.76
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 38.7
			},
			{
			  "name": "Management Score",
			  "score": 93.84
			},
			{
			  "name": "Shareholders",
			  "score": 17.93
			}
		  ],
		  "news": null,
		  "outlierScore": 31,
		  "refinitivNormalisedScore": 0.638,
		  "sustainalyticsNormalisedScore": 0.33,
		  "refinitivESGCombinedScore": 64.044,
		  "sustainalyticsESGCombinedScore": 22,
		  "isOutLier": true,
		  "isinType": null
		},
		{
		  "isin": "US5324571083",
		  "name": "Eli Lilly and Company",
		  "wt": 0,
		  "esgScore": 65.92,
		  "environmentalScore": 78.06,
		  "socialScore": 84.72,
		  "governenceScore": 28.56,
		  "holdingValue": 2,
		  "controversyScore": 45.61,
		  "esgCombinedScore": 55.77,
		  "totalESGCombinedScore": 1.12,
		  "totalESGScore": 1.32,
		  "totalEnvironmentalScore": 1.56,
		  "totalSocialScore": 1.69,
		  "totalGovernanceScore": 0.57,
		  "influenceESGCombinedScore": 0.05,
		  "influenceESGSCore": -0.12,
		  "influenceEnvironmentalScore": 0.15,
		  "influenceSocialScore": 0.23,
		  "influenceGovernanceScore": -1.08,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 71.38
			},
			{
			  "name": "Environmental Innovation",
			  "score": 57.41
			},
			{
			  "name": "Resource Use",
			  "score": 90.27
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 91.06
			},
			{
			  "name": "Human Rights",
			  "score": 67.83
			},
			{
			  "name": "Product Responsibility",
			  "score": 96.08
			},
			{
			  "name": "WorkForce",
			  "score": 91.51
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 62.87
			},
			{
			  "name": "Management Score",
			  "score": 23.91
			},
			{
			  "name": "Shareholders",
			  "score": 21.17
			}
		  ],
		  "news": null,
		  "outlierScore": 17,
		  "refinitivNormalisedScore": 0.497,
		  "sustainalyticsNormalisedScore": 0.327,
		  "refinitivESGCombinedScore": 55.768,
		  "sustainalyticsESGCombinedScore": 34,
		  "isOutLier": false,
		  "isinType": null
		},
		{
		  "isin": "US88579Y1010",
		  "name": "3M Company",
		  "wt": 0,
		  "esgScore": 84.65,
		  "environmentalScore": 80.38,
		  "socialScore": 96.3,
		  "governenceScore": 71.93,
		  "holdingValue": 3,
		  "controversyScore": 34.21,
		  "esgCombinedScore": 59.43,
		  "totalESGCombinedScore": 1.78,
		  "totalESGScore": 2.54,
		  "totalEnvironmentalScore": 2.41,
		  "totalSocialScore": 2.89,
		  "totalGovernanceScore": 2.16,
		  "influenceESGCombinedScore": 0.27,
		  "influenceESGSCore": 0.62,
		  "influenceEnvironmentalScore": 0.33,
		  "influenceSocialScore": 0.81,
		  "influenceGovernanceScore": 0.46,
		  "environmentalFactors": [
			{
			  "name": "Emission",
			  "score": 79.79
			},
			{
			  "name": "Environmental Innovation",
			  "score": 75.68
			},
			{
			  "name": "Resource Use",
			  "score": 87.5
			}
		  ],
		  "socialFactors": [
			{
			  "name": "Community",
			  "score": 98.98
			},
			{
			  "name": "Human Rights",
			  "score": 94.74
			},
			{
			  "name": "Product Responsibility",
			  "score": 95.65
			},
			{
			  "name": "WorkForce",
			  "score": 96.94
			}
		  ],
		  "governanceFactors": [
			{
			  "name": "CSR Strategy",
			  "score": 85.15
			},
			{
			  "name": "Management Score",
			  "score": 79.84
			},
			{
			  "name": "Shareholders",
			  "score": 36.74
			}
		  ],
		  "news": null,
		  "outlierScore": 20,
		  "refinitivNormalisedScore": 0.687,
		  "sustainalyticsNormalisedScore": 0.488,
		  "refinitivESGCombinedScore": 59.428,
		  "sustainalyticsESGCombinedScore": 35,
		  "isOutLier": false,
		  "isinType": null
		}
	  ]
	}
    ```