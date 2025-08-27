package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * The type Currency allocation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyAllocation implements Serializable {
    /**
     * The Currency allocation.
     */
    private Map<String,Double> currencyAllocation;

}
