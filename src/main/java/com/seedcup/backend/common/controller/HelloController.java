package com.seedcup.backend.common.controller;

import com.seedcup.backend.common.annotation.LoginRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/test")
public class HelloController {

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {
        /*
         * @Author holdice
         * @Description
         * @Date 2020/11/20 11:45 下午
         * @Param java.lang.String
         * @return java.lang.String
         **/
        return "Hello";
    }
}
