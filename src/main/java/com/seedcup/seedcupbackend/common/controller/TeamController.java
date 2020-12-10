package com.seedcup.seedcupbackend.common.controller;


import com.alibaba.fastjson.JSONObject;
import com.seedcup.seedcupbackend.common.annotation.LoginRequired;
import com.seedcup.seedcupbackend.common.dto.TeamEditIntroductionDto;
import com.seedcup.seedcupbackend.common.dto.TeamSignUpDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateInfoException;
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
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping(value = "/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @LoginRequired
    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public ResponseDto<Object> signUp(@RequestBody TeamSignUpDto signUpDto) {
        /*
         * @Author icer
         * @Description 注册队伍
         * @Date 2020/11/26 17:26
         * @Param [signUpDto]
         * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
         */
        try {
            teamService.signUp(signUpDto);
            return StandardResponse.ok();
        } catch (DuplicateInfoException e) {
            return StandardResponse.duplicateInformation(e.getDuplicateInfos());
        }
    }

    @LoginRequired
    @RequestMapping(value = "/edit_introduction", method = RequestMethod.POST)
    public ResponseDto<Object> editIntroduction(@RequestBody TeamEditIntroductionDto eidtIntroductionDto) {
        /*
        * @Author icer
        * @Description 修改队伍信息
        * @Date 2020/12/9 19:57
        * @Param [eidtIntroductionDto]
        * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
        */
        teamService.editIntroduction(eidtIntroductionDto);
        return StandardResponse.ok();
    }

    @LoginRequired
    @RequestMapping(value = "/addmember/{userId}", method = RequestMethod.POST)
    public ResponseDto<Object> addMember(@PathVariable (name = "userId") Integer userId) {
        /*
        * @Author icer
        * @Description 添加队员
        * @Date 2020/12/9 19:58
        * @Param [request]
        * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
        */
        teamService.addMember(userId);
        return StandardResponse.ok();
    }

    @LoginRequired
    @RequestMapping(value = "/delmember/{userId}", method = RequestMethod.POST)
    public ResponseDto<Object> delMember(@PathVariable (name = "userId") Integer userId) {
        /*
        * @Author icer
        * @Description 删除队伍中的队员
        * @Date 2020/12/10 15:25
        * @Param [userId]
        * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
        */
        teamService.delMember(userId);
        return StandardResponse.ok();
    }

    @LoginRequired
    @RequestMapping(value = "/get_all_team_member/{teamId}", method = RequestMethod.POST)
    public ResponseDto<Object> getAllTeamMember(@PathVariable(name = "teamId") Integer teamId){
        /*
        * @Author icer
        * @Description 获取队伍中所有成员列表，传入参数为队伍id
        * @Date 2020/12/10 16:11
        * @Param [teamId]
        * @return com.seedcup.seedcupbackend.global.dto.ResponseDto<java.lang.Object>
        */
        return StandardResponse.ok(teamService.getAllTeamMember(teamId));
    }
}