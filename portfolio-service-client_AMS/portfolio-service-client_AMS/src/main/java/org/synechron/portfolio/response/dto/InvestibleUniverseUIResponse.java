package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestibleUniverseUIResponse implements Serializable {

    private List<InvestableUniverseCompanies> companies;
    private InvestibleUniverseFilterUIResponseDto investibleUniverseFilter;
}
