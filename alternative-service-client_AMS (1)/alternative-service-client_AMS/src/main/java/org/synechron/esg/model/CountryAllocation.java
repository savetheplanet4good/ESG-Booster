package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * The type Country allocation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryAllocation implements Serializable {
    /**
     * The Country allocation.
     */
    private Map<String,Double> countryAllocation;
}
