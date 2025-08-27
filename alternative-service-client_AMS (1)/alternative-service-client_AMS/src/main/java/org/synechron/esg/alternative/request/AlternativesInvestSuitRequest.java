package org.synechron.esg.alternative.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * The type Alternatives invest suit request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativesInvestSuitRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "current_pf")
    private Map<String, Double> current_pf;
    @JsonProperty(value = "instr_to_replace")
    private List<String> instr_to_replace;
    @JsonProperty(value = "investable_universe")
    private List<String> investable_universe;
    @JsonProperty(value = "esg_source")
    private String esg_source;
    @JsonProperty(value = "esg_target")
    private String esg_target;

}
