package org.synechron.esg.masteruniverse.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.esg.masteruniverse.response.ExceptionResponse;

@RestController
public class BaseController {

    private static final Logger LOGGER = LogManager.getLogger(BaseController.class);


    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleCustomException(Exception ex) {
        LOGGER.error("Exception : {}", ex.getStackTrace());
        ex.printStackTrace();
        return new ExceptionResponse("FAIL", "These is some issue at server , please contact system administrator");

    }

}
