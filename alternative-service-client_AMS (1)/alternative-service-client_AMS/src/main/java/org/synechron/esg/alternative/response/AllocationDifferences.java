package org.synechron.esg.alternative.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * The type Allocation differences.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocationDifferences {
    /**
     * The Sector.
     */
    Map<String, Double> sector;
    /**
     * The Region.
     */
    Map<String, Double> region;
    /**
     * The Currency.
     */
    Map<String, Double> currency;

}
