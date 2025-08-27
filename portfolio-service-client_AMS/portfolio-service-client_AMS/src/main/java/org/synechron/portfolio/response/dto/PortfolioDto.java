package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.synechron.esg.model.Equity;
import org.synechron.esg.model.Fund;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDto {

    private String portfolioId;
    private String portfolioName;
    private String portfolioType;
    private String investableUniverseType;
    private Double esgCombinedScore;
    private Double esgScore;
    private Double envScore;
    private Double socialScore;
    private Double govScore;
    private List<FactorDto> environmentalFactors;
    private List<FactorDto> socialFactors;
    private List<FactorDto> governanceFactors;
    private int companyCount;
    private int outLierCount;
    private Fund fund;
    private Equity equity;
    private String calculationType;
    private String portfolioIsinsType;
    private String esgMsciScore;
}
