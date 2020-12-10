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
import org.apache.logging.log4j.util.IndexedReadOnlyStringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void signUp(TeamSignUpDto signUpDto) throws DuplicateInfoException {
        /*
        * @Author icer
        * @Description 创建队伍
        * @Date 2020/12/9 20:24
        * @Param [signUpDto]
        * @return void
        */
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
        /*
        * @Author icer
        * @Description 修改队伍信息
        * @Date 2020/12/9 20:25
        * @Param [editIntroductionDto]
        * @return void
        */
        String newIntroduction = editIntroductionDto.getIntroduction();
        UpdateWrapper<Team> uw = new UpdateWrapper<>();
        uw.eq("leader_id", AuthInterceptor.getCurrentUser().getId());
        Team team = new Team();
        team.setIntroduction(newIntroduction);
        teamMapper.update(team, uw);
    }

    @Override
    public void addMember(Integer userId){
        /*
        * @Author icer
        * @Description 队伍添加新成员
        * @Date 2020/12/9 20:25
        * @Param [usernameOrPhoneNumberOrEmail]
        * @return void
        *
        */
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.eq("id", userId);
        int teamId = AuthInterceptor.getCurrentUser().getTeamId();
        User user = new User();
        user.setTeamId(teamId);
        userMapper.update(user, uw);
    }

    @Override
    public void delMember(Integer userId){
        /*
        * @Author icer
        * @Description 从队伍中删除队员
        * @Date 2020/12/9 23:49
        * @Param [userId]
        * @return void
        */
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", userId);
        User user = new User();
        user.setTeamId(-1);
        userMapper.update(user, userUpdateWrapper);
    }

    @Override
    public List<User> getAllTeamMember(Integer teamId) {
        /*
        * @Author icer
        * @Description 获取队伍中所有成员列表，传入参数为队伍id
        * @Date 2020/12/10 16:13
        * @Param [teamId]
        * @return java.util.List<com.seedcup.seedcupbackend.common.po.User>
        */
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("team_id", teamId);
        return userMapper.selectList(userQueryWrapper);
    }
}
