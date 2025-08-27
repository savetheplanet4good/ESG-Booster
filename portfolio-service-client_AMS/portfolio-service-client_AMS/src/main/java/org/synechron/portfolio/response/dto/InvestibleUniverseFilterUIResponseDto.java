package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestibleUniverseFilterUIResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int esg;
    private int environmental;
    private int social;
    private int governance;
    private List<String> selectedCountries;
    private List<String> selectedSectors;
}
