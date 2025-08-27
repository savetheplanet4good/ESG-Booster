package org.synechron.portfolio.response.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.synechron.portfolio.request.AlternativesInvestSuitRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Document(collection = "failedInvestAlternativesRequest")
@NoArgsConstructor
@AllArgsConstructor
public class FailedInvestSuiteRequests implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @Field(name="FailedInvestSuiteRequestsId")
    private Integer failedInvestSuiteRequestsId;
    @Field(name="alternativeRequest")
	private AlternativesInvestSuitRequest requestAlt;
    @Field(name="alternativeResponse")
	private String response;
    @Field(name="CreatedDate")
	private Date createdDate;
}
