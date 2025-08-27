package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * The type Investable universe filter.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestableUniverseFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    private int esg;
    private int environmental;
    private int social;
    private int governance;
    private List<String> selectedCountries;
    private List<String> selectedSectors;
}
