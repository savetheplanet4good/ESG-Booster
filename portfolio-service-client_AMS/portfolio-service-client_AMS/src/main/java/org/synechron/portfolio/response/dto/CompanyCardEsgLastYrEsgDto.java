package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyCardEsgLastYrEsgDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String isin;
	private String companyName;
	private String generalName;
	private String wikiLink;
	private String industry;
	private String leadership;
	private String revenue;
	private String website;
	private String description;
	private String logoUrl;
	private Double esgScore;
	private Double esgCombinedScore;
	private Double esgControversiesScore;
	private Double envScore;
	private Double socialScore;
	private Double govScore;
	private Double newsSentimentScore;
	private LastYearESGScores lastYearESGScores;
}
