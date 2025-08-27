package org.synechron.esg.alternative.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Failed invest suite requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FailedInvestSuiteRequests implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer failedInvestSuiteRequestsId;
	private AlternativesInvestSuitRequest requestAlt;
	private String response;
	private Date createdDate;
	
	
}
