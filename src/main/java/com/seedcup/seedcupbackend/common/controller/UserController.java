package com.seedcup.seedcupbackend.common.controller;

import com.seedcup.seedcupbackend.common.exception.DuplicateUserInfoException;
import com.seedcup.seedcupbackend.common.pojo.UserSignUpDto;
import com.seedcup.seedcupbackend.common.service.UserService;
import com.seedcup.seedcupbackend.utils.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户相关路由控制器")
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册")
    public ResponseDto<Object> signIn(UserSignUpDto signUpDto) {
        try {
            userService.signUp(signUpDto);
            return new ResponseDto<>("200", "success");
        } catch (DuplicateUserInfoException e) {
            return new ResponseDto<>("400", "failure", e.getDuplicateInfos());
        }
    }
}
