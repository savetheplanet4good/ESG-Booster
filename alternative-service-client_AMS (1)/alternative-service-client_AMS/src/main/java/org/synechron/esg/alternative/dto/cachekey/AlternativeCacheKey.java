package org.synechron.esg.alternative.dto.cachekey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.synechron.esg.model.InvestableUniverseFilter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Alternative cache key.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeCacheKey implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /**
     * The Companies.
     */
    List<String> companies;
    /**
     * The Filter.
     */
    InvestableUniverseFilter filter;
    /**
     * The Type.
     */
//List<String> investableUniverse;
    String type;
    /**
     * The Current portfolio.
     */
    Map<String,Double> currentPortfolio;
    /**
     * The Esg target type.
     */
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
