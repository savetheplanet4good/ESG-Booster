package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EsgRateDistributionsDTO {

	private String name;
	private Double score;

	@Override
	public String toString() {
		return "EsgRateDistributionsDTO{" +
				"name='" + name + '\'' +
				", score=" + score +
				'}';
	}
}
