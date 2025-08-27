package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeCacheKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> companies;
    //private List<String> investableUniverse;
    private InvestableUniverseFilter filter;
    String type;
    private Map<String,Double> currentPortfolio;
    String esgTargetType;

    @Override
    public int hashCode(){

        return Objects.hash(companies,filter,type,currentPortfolio,esgTargetType);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlternativeCacheKey that = (AlternativeCacheKey) o;
        return companies.equals(that.companies) &&
                filter.equals(that.filter) &&
                type.equals(that.type)&&currentPortfolio.equals(that.currentPortfolio)&&esgTargetType.equals(that.esgTargetType);
    }
}
