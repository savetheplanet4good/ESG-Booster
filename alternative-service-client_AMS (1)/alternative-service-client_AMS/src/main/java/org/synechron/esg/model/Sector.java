package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * The type Sector.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sector implements Serializable {
    /**
     * The Sectors.
     */
    private Map<String,Double> sectors;
}
