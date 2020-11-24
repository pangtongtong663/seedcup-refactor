package com.seedcup.seedcupbackend.common.controller;

import com.seedcup.seedcupbackend.common.annotation.LoginRequired;
import com.seedcup.seedcupbackend.common.interceptor.AuthInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@Api(tags = "测试用示范接口")
@RequestMapping(value = "/api/test")
public class HelloController {

    @LoginRequired
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ApiOperation(value = "测试接口，hello，world")
    public String index(HttpSession session) {
        /*
         * @Author holdice
         * @Description
         * @Date 2020/11/20 11:45 下午
         * @Param java.lang.String
         * @return java.lang.String
         **/
        return "Hello " + session.getAttribute("userInfo") + " " + session.getMaxInactiveInterval() + AuthInterceptor.getCurrentUser().toString();
    }
}
