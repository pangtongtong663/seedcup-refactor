package com.seedcup.seedcupbackend.common.controller;

import com.seedcup.seedcupbackend.common.annotation.LoginRequired;
import com.seedcup.seedcupbackend.common.dto.UserLoginDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateInfoException;
import com.seedcup.seedcupbackend.common.dto.UserSignUpDto;
import com.seedcup.seedcupbackend.common.po.User;
import com.seedcup.seedcupbackend.common.service.UserService;
import com.seedcup.seedcupbackend.global.dto.ResponseDto;
import com.seedcup.seedcupbackend.global.dto.StandardResponse;
import com.seedcup.seedcupbackend.global.exception.SmsCaptchaWrongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
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
        } catch (SmsCaptchaWrongException e) {
            return StandardResponse.captchaError();
        } catch (DuplicateInfoException e) {
            return StandardResponse.duplicateInformation(e.getDuplicateInfos());
        }
    }

    @RequestMapping(value = "/log_in", method = RequestMethod.POST)
    public ResponseDto<Object> logIn(@Valid @RequestBody UserLoginDto loginInfo, HttpSession session) {
        /*
         * @Author holdice
         * @Description 用户登录接口
         * @Date 2020/12/7 9:07 下午
         * @Param [loginInfo, session]
         * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
         */
        User user = userService.logIn(loginInfo);
        if (user != null) {
            session.setAttribute("userInfo", user);
            return StandardResponse.ok();
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired
    @RequestMapping(value = "/log_out", method = RequestMethod.POST)
    public ResponseDto<Object> logOut(HttpSession session) {
        /*
         * @Author holdice
         * @Description 注销登录
         * @Date 2020/12/7 9:07 下午
         * @Param [session]
         * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
         */
        userService.logOut(session);
        return StandardResponse.ok();
    }

    @LoginRequired
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseDto<Object> searchUserByKeyword(@RequestParam String keyword) {
        return StandardResponse.ok(userService.searchUser(keyword));
    }

    @LoginRequired
    @RequestMapping(value = "/my_info", method = RequestMethod.GET)
    public ResponseDto<Object> getCurrentUserInfo() {
        /*
         * @Author holdice
         * @Description 获取当前用户信息
         * @Date 2020/12/7 9:08 下午
         * @Param []
         * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
         */
        return StandardResponse.ok(userService.getCurrentUser());
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseDto<Object> getAllUsers() {
        /*
         * @Author holdice
         * @Description 获取所有用户，需要管理员权限，可以看到管理员用户
         * @Date 2020/12/9 11:25 下午
         * @Param []
         * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
         */
        return StandardResponse.ok(userService.getAllUsers());
    }
}
