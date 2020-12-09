package com.seedcup.seedcupbackend.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.seedcup.seedcupbackend.common.dao.TeamMapper;
import com.seedcup.seedcupbackend.common.dao.UserMapper;
import com.seedcup.seedcupbackend.common.dto.TeamEditIntroductionDto;
import com.seedcup.seedcupbackend.common.dto.TeamSignUpDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateInfoException;
import com.seedcup.seedcupbackend.common.exception.UserNotExistException;
import com.seedcup.seedcupbackend.common.interceptor.AuthInterceptor;
import com.seedcup.seedcupbackend.common.po.Team;
import com.seedcup.seedcupbackend.common.po.User;
import com.seedcup.seedcupbackend.common.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void signUp(TeamSignUpDto signUpDto) throws DuplicateInfoException {
        DuplicateInfoException e = new DuplicateInfoException();
        QueryWrapper<Team> qw = new QueryWrapper<>();
        qw.eq("name", signUpDto.getTeamName());
        if(teamMapper.selectList(qw).size() != 0) {
            e.addDuplicateInfoField("teamName");
        }
        if(teamMapper.selectList(qw).size() == 0){
            Team newTeam = Team.builder()
                    .name(signUpDto.getTeamName())
                    .highestGrade(signUpDto.getHighestGrade())
                    .introduction(signUpDto.getIntroduction())
                    .leaderId(AuthInterceptor.getCurrentUser().getId())
                    .build();
            teamMapper.insert(newTeam);
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("id", newTeam.getLeaderId());
            User user = new User();
            user.setTeamId(newTeam.getId());
            userMapper.update(user, userUpdateWrapper);
            log.info(newTeam.toString());
        }
        else{
            throw e;
        }
    }

    @Override
    public void editIntroduction(TeamEditIntroductionDto editIntroductionDto) {
        String newIntroduction = editIntroductionDto.getIntroduction();
        UpdateWrapper<Team> uw = new UpdateWrapper<>();
        uw.eq("leader_id", AuthInterceptor.getCurrentUser().getId());
        Team team = new Team();
        team.setIntroduction(newIntroduction);
        teamMapper.update(team, uw);
    }

    @Override
    public void addMember(String usernameOrPhoneNumberOrEmail) throws UserNotExistException {
        UserNotExistException e = new UserNotExistException();
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", usernameOrPhoneNumberOrEmail)
                .or()
                .eq("phone_number", usernameOrPhoneNumberOrEmail)
                .or()
                .eq("email", usernameOrPhoneNumberOrEmail);
        if(userMapper.selectList(qw).size() != 0) {
            UpdateWrapper<User> uw = new UpdateWrapper<>();
            uw.eq("username", usernameOrPhoneNumberOrEmail)
                    .or()
                    .eq("phone_number", usernameOrPhoneNumberOrEmail)
                    .or()
                    .eq("email", usernameOrPhoneNumberOrEmail);
            int teamId = AuthInterceptor.getCurrentUser().getTeamId();
            User user = new User();
            user.setTeamId(teamId);
            userMapper.update(user, uw);
        }
        else{
            throw e;
        }

    }
}
