package com.seedcup.seedcupbackend.common.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.seedcup.seedcupbackend.common.annotation.LoginRequired;
import com.seedcup.seedcupbackend.common.dto.UserLoginDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateUserInfoException;
import com.seedcup.seedcupbackend.common.dto.UserSignUpDto;
import com.seedcup.seedcupbackend.common.po.User;
import com.seedcup.seedcupbackend.common.service.UserService;
import com.seedcup.seedcupbackend.global.dto.ResponseDto;
import com.seedcup.seedcupbackend.global.dto.StandardResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "用户相关路由")
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册", notes = "注意用户注册参数合法性,如果遇到注册用户名、邮箱或者电话号码已存在，则返回code=101，并在data里附上重复的字段名list")
    @ApiResponses({
            @ApiResponse(code = 101, message = "duplicate user information",
                    response = String.class, responseContainer = "list")
    })
    public ResponseDto<Object> signUp(@Valid @RequestBody UserSignUpDto signUpDto) {
        /*
         * @Author holdice
         * @Description 用户注册接口
         * @Date 2020/11/22 下午7:36
         * @Param [signUpDto]
         * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
         */
        try {
            userService.signUp(signUpDto);
            return StandardResponse.ok();
        } catch (DuplicateUserInfoException e) {
            return StandardResponse.duplicateInformation(e.getDuplicateInfos());
        }
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", notes = "使用session维持用户登录状态", code = 0)
    public ResponseDto<Object> logIn(@Valid @RequestBody UserLoginDto loginInfo, HttpSession session) {
        User user  = userService.logIn(loginInfo);
        if (user != null) {
            session.setAttribute("userInfo", user);
            return StandardResponse.ok();
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired
    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    @ApiOperation(value = "注销登录", notes = "需要登录，即没登录的时候无权调用此接口")
    public ResponseDto<Object> logOut(HttpSession session) {
        userService.logOut(session);
        return StandardResponse.ok();
    }
}
