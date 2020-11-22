package com.seedcup.seedcupbackend.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class ApiDocController {

    @RequestMapping(value = "/api/doc", method = RequestMethod.GET)
    public String ApiSwaggerDoc() {
        return "redirect:/swagger-ui.html";
    }
}
