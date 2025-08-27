package org.synechron.esg.alternative.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Historical data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalData implements Serializable {
    private String data;
    private Double value;
}
