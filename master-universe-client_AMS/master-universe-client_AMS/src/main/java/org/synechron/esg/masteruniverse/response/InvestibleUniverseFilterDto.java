package org.synechron.esg.masteruniverse.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestibleUniverseFilterDto {
    private int esg;
    private int environmental;
    private int social;
    private int governance;
    private List<String> selectedCountries;
    private List<String> selectedSectors;

    private int susEsg;
    private int susEnvironmental;
    private int susSocial;
    private int susGovernance;

    private int fundEsgScore;
}
