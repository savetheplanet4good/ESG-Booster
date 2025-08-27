package org.synechron.esg.masteruniverse.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.synechron.esg.model.AlternativesCompany;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestibleUniverseResponse {

    private List<AlternativesCompany> companies;
    private InvestibleUniverseFilterDto investibleUniverseFilter;

}
