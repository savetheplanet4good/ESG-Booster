package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EsgRateDistributionResponseDTO {

	private List<EsgRateDistributionsDTO> esgRateDistributionsDTOList;
	private int legends;
	private int laggards;
}
