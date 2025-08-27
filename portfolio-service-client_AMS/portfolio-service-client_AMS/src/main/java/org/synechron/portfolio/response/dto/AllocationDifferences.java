package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocationDifferences {

    Map<String, Double> sector;
    Map<String, Double> region;
    Map<String, Double> currency;
}
