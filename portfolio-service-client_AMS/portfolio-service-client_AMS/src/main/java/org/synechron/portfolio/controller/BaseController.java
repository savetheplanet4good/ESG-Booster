package org.synechron.portfolio.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.portfolio.response.dto.ExceptionResponse;

@RestController
public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);


    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleCustomException(Exception ex) {
        LOGGER.error("Exception : {}", ex.getStackTrace());
        ex.printStackTrace();
        return new ExceptionResponse("FAIL", "These is some issue at server , please contact system administrator");

    }

}
