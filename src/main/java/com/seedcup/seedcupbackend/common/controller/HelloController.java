package com.seedcup.seedcupbackend.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.seedcupbackend.common.pojo.User;
import com.seedcup.seedcupbackend.common.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "测试用示范接口")
@RequestMapping(value = "/test")
public class HelloController {

    @ApiOperation(value = "测试接口，hello，world", notes = "")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = -1, message = "error")})
    public String index() {
        /**
         * @Author holdice
         * @Description
         * @Date 2020/11/20 11:45 下午
         * @Param java.lang.String
         * @return java.lang.String
         **/
        return "Hello,World!";
    }
}
