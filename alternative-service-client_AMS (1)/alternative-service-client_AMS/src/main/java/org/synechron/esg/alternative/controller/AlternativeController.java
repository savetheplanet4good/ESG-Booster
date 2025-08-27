package org.synechron.esg.alternative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.esg.alternative.request.AlternativeRequest;
import org.synechron.esg.alternative.response.AlternativeResponse;
import org.synechron.esg.alternative.service.AlternativeService;

/**
 * The type Alternative controller.
 */
@RestController
public class AlternativeController {

    @Autowired
    private AlternativeService alternativeService;


    /**
     * Get alternative alternative response.
     *
     * @param alternativeRequest the alternative request
     * @return the alternative response
     */
    @PostMapping("/alternatives")
    public AlternativeResponse getAlternative(@RequestBody AlternativeRequest alternativeRequest){

        AlternativeResponse alternativeResponse = alternativeService.getAlternatives(alternativeRequest);
        return alternativeResponse;
    }
}
