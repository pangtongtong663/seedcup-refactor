package com.seedcup.backend.common.controller;

import com.seedcup.backend.common.annotation.LoginRequired;
import com.seedcup.backend.common.dto.UserBasicInfo;
import com.seedcup.backend.common.dto.UserLoginDto;
import com.seedcup.backend.common.exception.DuplicateInfoException;
import com.seedcup.backend.common.dto.UserSignUpDto;
import com.seedcup.backend.common.po.User;
import com.seedcup.backend.common.service.UserService;
import com.seedcup.backend.global.dto.ResponseDto;
import com.seedcup.backend.global.dto.StandardResponse;
import com.seedcup.backend.global.exception.SmsCaptchaWrongException;
import com.seedcup.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserController {

    @Autowired
    private JwtUtils jwt;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public ResponseDto<Object> signUp(@Valid @RequestBody UserSignUpDto signUpDto) {
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
    public ResponseDto<Object> logIn(@Valid @RequestBody UserLoginDto loginInfo) {
        User user = userService.logIn(loginInfo);
        if (user != null) {
            return StandardResponse.ok(jwt.generateJwtToken(user.getId()));
        } else {
            return StandardResponse.fail();
        }
    }

    @LoginRequired
    @RequestMapping(value = "/log_out", method = RequestMethod.POST)
    public ResponseDto<Object> logOut(HttpSession session) {
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
        return StandardResponse.ok(userService.getCurrentUser());
    }

    @LoginRequired(needAdmin = true)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseDto<Object> getAllUsers() {
        return StandardResponse.ok(userService.getAllUsers());
    }
}
