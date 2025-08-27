package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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

    private int susEsg;
    private int susEnvironmental;
    private int susSocial;
    private int susGovernance;

    private int fundEsgScore;

    @Override
    public String toString() {
        return "InvestableUniverseFilter{" +
                "esg=" + esg +
                ", environmental=" + environmental +
                ", social=" + social +
                ", governance=" + governance +
                ", selectedCountries=" + selectedCountries +
                ", selectedSectors=" + selectedSectors +
                ", susEsg=" + susEsg +
                ", susEnvironmental=" + susEnvironmental +
                ", susSocial=" + susSocial +
                ", susGovernance=" + susGovernance +
                ", fundEsgScore=" + fundEsgScore +
                '}';
    }
}
