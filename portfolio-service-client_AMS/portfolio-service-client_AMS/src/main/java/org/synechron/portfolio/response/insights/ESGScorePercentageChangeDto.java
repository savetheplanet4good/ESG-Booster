package org.synechron.portfolio.response.insights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ESGScorePercentageChangeDto {

	private String title;
	private String displayMessage;
	private Double percentageChange;
}
