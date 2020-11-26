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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
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
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseDto<Object> logOut(HttpSession session) {
        userService.logOut(session);
        return StandardResponse.ok();
    }
}
