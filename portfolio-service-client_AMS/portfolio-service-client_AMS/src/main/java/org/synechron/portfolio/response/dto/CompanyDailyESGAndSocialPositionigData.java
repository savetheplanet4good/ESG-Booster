package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDailyESGAndSocialPositionigData {

	private String isin;
	private Double environemntalScore;
	private Double socialScore;
	private Double governanceScore;
	private String ticker;
	private String scoreDate;
	private Double totalESGScore;
	private Double characterScore;
	private Double commitment;
	private Double exchangeOfBenefits;
	private Double general;
	private Double socialResponsibility;
	private Double influence;
	private Double satisfaction;
	private Double trust;
	private Double sectorAverage;
}
