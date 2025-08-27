package org.synechron.portfolio.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureImportanceResponse {

    Map<String, Double> esg_feature_importance;
    Map<String, Double> e_feature_importance;
    Map<String, Double> s_feature_importance;
    Map<String, Double> g_feature_importance;
}
