package com.seedcup.seedcupbackend.common.controller;


import com.alibaba.fastjson.JSONObject;
import com.seedcup.seedcupbackend.common.annotation.LoginRequired;
import com.seedcup.seedcupbackend.common.dto.TeamEditIntroductionDto;
import com.seedcup.seedcupbackend.common.dto.TeamSignUpDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateTeamInfoException;
import com.seedcup.seedcupbackend.common.exception.UserNotExistException;
import com.seedcup.seedcupbackend.common.service.TeamService;
import com.seedcup.seedcupbackend.global.dto.ResponseDto;
import com.seedcup.seedcupbackend.global.dto.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping(value = "/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @LoginRequired
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseDto<Object> signUp(@RequestBody TeamSignUpDto signUpDto) {
        /*
         * @Author icer
         * @Description
         * @Date 2020/11/26 17:26
         * @Param [signUpDto]
         * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
         */
        try {
            teamService.signUp(signUpDto);
            return StandardResponse.ok();
        } catch (DuplicateTeamInfoException e) {
            return StandardResponse.duplicateInformation(e.getDuplicateInfos());
        }
    }

    @LoginRequired
    @RequestMapping(value = "/edit_introduction", method = RequestMethod.POST)
    public ResponseDto<Object> editIntroduction(@RequestBody TeamEditIntroductionDto eidtIntroductionDto) {
        teamService.editIntroduction(eidtIntroductionDto);
        return StandardResponse.ok();
    }

    @LoginRequired
    @RequestMapping(value = "/addmember", method = RequestMethod.POST)
    public ResponseDto<Object> addMember(HttpServletRequest request) throws IOException {
        try {
            String usernameOrPhoneNumberOrEmail = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            if (!StringUtils.isEmpty(usernameOrPhoneNumberOrEmail)) {
                JSONObject jsonObject = JSONObject.parseObject(usernameOrPhoneNumberOrEmail);
                usernameOrPhoneNumberOrEmail = jsonObject.getString("usernameOrPhoneNumberOrEmail");
            }
            teamService.addMember(usernameOrPhoneNumberOrEmail);
            return StandardResponse.ok();
        } catch (UserNotExistException e){
            return StandardResponse.userNotExist();
        }
    }
}