package org.synechron.portfolio.response.insights;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EsgInfluenceCountResponseDto {

    private String title;
    private String displayMessage;
    private int positiveInfluenceCount;
    private int negativeInfluenceCount;
}
