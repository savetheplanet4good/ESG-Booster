package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyPeerAverageScore {

	private String isin;
	private String companyName;
	private String sector;
	private Double totalEsg;
	private Double avgEsg;
	private Double bestEsg;
	private Integer totalCount;
	private Integer rank;
	private String industryCategory;
	private Integer rankPercentile;
	private Integer esgOutlier;
	private Double totalEsgDs2;
	private Double avgEsgDs2;
	private Double bestEsgDs2;
	private Integer totalCountDs2;
	private Integer rankDs2;
	private Integer rankPercentileDs2;
	private Integer esgOutlierDs2;

	@Override
	public String toString() {
		return "CompanyPeerAverageScore{" +
				"isin='" + isin + '\'' +
				", companyName='" + companyName + '\'' +
				", sector='" + sector + '\'' +
				", totalEsg=" + totalEsg +
				", avgEsg=" + avgEsg +
				", bestEsg=" + bestEsg +
				", totalCount=" + totalCount +
				", rank=" + rank +
				", industryCategory='" + industryCategory + '\'' +
				", rankPercentile=" + rankPercentile +
				", esgOutlier=" + esgOutlier +
				'}';
	}
}
