package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.synechron.esg.model.AlternativesCompany;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestibleUniverseResponse implements Serializable {

    private List<AlternativesCompany> companies;
    private InvestibleUniverseFilterDto investibleUniverseFilter;
}
