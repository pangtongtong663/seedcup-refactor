package com.seedcup.backend.common.controller;

import com.seedcup.backend.common.annotation.LoginRequired;
import com.seedcup.backend.common.annotation.TeamRequired;
import com.seedcup.backend.common.dto.TeamInfoDto;
import com.seedcup.backend.common.dto.TeamUpdateDto;
import com.seedcup.backend.common.dto.TeamCreateDto;
import com.seedcup.backend.common.exception.*;
import com.seedcup.backend.common.service.TeamService;
import com.seedcup.backend.global.dto.ResponseDto;
import com.seedcup.backend.global.dto.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @LoginRequired
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseDto<Object> createTeam(@RequestBody TeamCreateDto signUpDto) {
        try {
            teamService.createTeam(signUpDto);
            return StandardResponse.ok();
        } catch (AlreadyInTeamException e) {
            return StandardResponse.alreadyInTeam();
        } catch (DuplicateInfoException e) {
            return StandardResponse.duplicateInformation(e.getDuplicateInfos());
        }
    }

    @LoginRequired
    @TeamRequired(needLeader = true)
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseDto<Object> editTeamInfo(@RequestBody TeamUpdateDto teamUpdateDto) {
        teamService.editTeamInfo(teamUpdateDto);
        return StandardResponse.ok();
    }

    @LoginRequired
    @TeamRequired(needLeader = true)
    @RequestMapping(value = "/member/add/{userId}", method = RequestMethod.POST)
    public ResponseDto<Object> addMember(@PathVariable (name = "userId") Integer userId) {
        try {
            teamService.addMember(userId);
            return StandardResponse.ok();
        } catch (AlreadyInTeamException e) {
            return StandardResponse.alreadyInTeam();
        }
    }

    @LoginRequired
    @TeamRequired(needLeader = true)
    @RequestMapping(value = "/member/delete/{userId}", method = RequestMethod.POST)
    public ResponseDto<Object> delMember(@PathVariable (name = "userId") Integer userId) {
        teamService.delMember(userId);
        return StandardResponse.ok();
    }

    @LoginRequired
    @RequestMapping(value = "/info/{teamId}", method = RequestMethod.GET)
    public ResponseDto<Object> getTeamInfo(@PathVariable Integer teamId){
        try {
            return StandardResponse.ok(teamService.getTeamInfo(teamId));
        } catch (NoTeamException e) {
            return StandardResponse.notInTeam();
        }

    }
}