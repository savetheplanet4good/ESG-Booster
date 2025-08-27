package org.synechron.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.portfolio.request.ReplaceCompaniesRequest;
import org.synechron.portfolio.response.dto.ReplaceCompanyResponse;
import org.synechron.portfolio.service.ReplaceCompaniesService;

@RestController
public class ReplaceController extends BaseController {

    @Autowired
    private ReplaceCompaniesService replaceCompaniesService;

    @PostMapping("/replace")
    public ReplaceCompanyResponse replaceCompanies(@RequestBody ReplaceCompaniesRequest replaceCompaniesRequest) {

        return replaceCompaniesService.replaceCompanies(replaceCompaniesRequest);

    }
}
