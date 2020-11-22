package com.seedcup.seedcupbackend.common.controller;

import com.seedcup.seedcupbackend.common.exception.DuplicateUserInfoException;
import com.seedcup.seedcupbackend.common.dto.UserSignUpDto;
import com.seedcup.seedcupbackend.common.service.UserService;
import com.seedcup.seedcupbackend.global.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@Api(tags = "用户相关路由控制器")
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册")
    public ResponseDto<Object> signIn(@Valid @RequestBody UserSignUpDto signUpDto) {
        /*
         * @Author holdice
         * @Description 用户注册接口
         * @Date 2020/11/22 下午7:36
         * @Param [signUpDto]
         * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
         */
        try {
            userService.signUp(signUpDto);
            return new ResponseDto<>("0", "success");
        } catch (DuplicateUserInfoException e) {
            return new ResponseDto<>("100", "failure", e.getDuplicateInfos());
        }
    }
}