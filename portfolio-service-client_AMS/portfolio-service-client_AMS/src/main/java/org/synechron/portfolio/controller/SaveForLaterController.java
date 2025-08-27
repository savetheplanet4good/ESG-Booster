package org.synechron.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.portfolio.request.SaveForLaterRequest;
import org.synechron.portfolio.response.dto.SaveForLaterResponse;
import org.synechron.portfolio.service.SaveForLaterService;

@RestController
public class SaveForLaterController extends BaseController{

    @Autowired
    private SaveForLaterService saveForLaterService;

    @PostMapping("/save-for-later")
    public SaveForLaterResponse saveForLaterPortfolio(@RequestBody SaveForLaterRequest saveForLaterRequest){
        return saveForLaterService.saveForLaterPortfolio(saveForLaterRequest);

    }
}
