package org.synechron.portfolio.response.history;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyHistoricalResponse {

	private List<Double> esgScore;
	private List<Double> envScore;
	private List<Double> socialScore;
	private List<Double> govScore;
	private List<Double> esgCombinedScore;
	private List<Double> sectorAvgScore;
	private List<String> year;
	private List<String> esgMsciScore;
}
