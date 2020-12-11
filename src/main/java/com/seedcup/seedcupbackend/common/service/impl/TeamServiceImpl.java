package com.seedcup.seedcupbackend.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.seedcup.seedcupbackend.common.dao.TeamMapper;
import com.seedcup.seedcupbackend.common.dao.UserMapper;
import com.seedcup.seedcupbackend.common.dto.TeamUpdateDto;
import com.seedcup.seedcupbackend.common.dto.TeamCreateDto;
import com.seedcup.seedcupbackend.common.exception.*;
import com.seedcup.seedcupbackend.common.interceptor.AuthInterceptor;
import com.seedcup.seedcupbackend.common.po.Team;
import com.seedcup.seedcupbackend.common.po.User;
import com.seedcup.seedcupbackend.common.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void createTeam(TeamCreateDto teamCreateDto) throws DuplicateInfoException, AlreadyInTeamException {
        /*
        * @Author icer
        * @Description 创建队伍
        * @Date 2020/12/9 20:24
        * @Param [teamCreateDto]
        * @return void
        */
        checkCurrentUserTeam(false, false);
        DuplicateInfoException e = new DuplicateInfoException();
        QueryWrapper<Team> qw = new QueryWrapper<>();
        qw.eq("name", teamCreateDto.getTeamName());
        if(teamMapper.selectList(qw).size() != 0) {
            e.addDuplicateInfoField("teamName");
        }
        if(teamMapper.selectList(qw).size() == 0){
            Team newTeam = Team.builder()
                    .name(teamCreateDto.getTeamName())
                    .highestGrade(teamCreateDto.getHighestGrade())
                    .introduction(teamCreateDto.getIntroduction())
                    .leaderId(AuthInterceptor.getCurrentUser().getId())
                    .build();
            teamMapper.insert(newTeam);
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("id", newTeam.getLeaderId());
            User user = new User();
            user.setTeamId(newTeam.getId());
            userMapper.update(user, userUpdateWrapper);
            log.info(newTeam.toString());
        } else{
            throw e;
        }
    }

    @Override
    public void editTeamInfo(TeamUpdateDto teamUpdateDto) throws NoTeamException, PermissionDeniedException {
        /*
        * @Author icer
        * @Description 修改自己队伍的信息
        * @Date 2020/12/9 20:25
        * @Param [editIntroductionDto]
        * @return void
        */
        checkCurrentUserTeam(true, true);

        String newIntroduction = teamUpdateDto.getIntroduction();
        UpdateWrapper<Team> uw = new UpdateWrapper<>();
        uw.eq("leader_id", AuthInterceptor.getCurrentUser().getId());
        Team team = new Team();
        team.setIntroduction(newIntroduction);
        teamMapper.update(team, uw);
    }

    @Override
    public void addMember(Integer userId) throws NoTeamException, PermissionDeniedException, AlreadyInTeamException {
        /*
        * @Author icer
        * @Description 队伍添加新成员
        * @Date 2020/12/9 20:25
        * @Param [usernameOrPhoneNumberOrEmail]
        * @return void
        *
        */
        checkCurrentUserTeam(true,true);
        //判断目标用户是否是自己
        if (userId.equals(AuthInterceptor.getCurrentUserId())) throw new PermissionDeniedException();
        //判断目标是否已有队伍，如果有，抛出异常
        if (userMapper.selectById(userId).getTeamId() != -1) throw new AlreadyInTeamException();
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.eq("id", userId);
        int teamId = AuthInterceptor.getCurrentUser().getTeamId();
        User user = new User();
        user.setTeamId(teamId);
        userMapper.update(user, uw);
    }

    private void checkCurrentUserTeam(boolean inTeam, boolean isLeader) throws NoTeamException, AlreadyInTeamException, PermissionDeniedException {
        /*
         * @Author holdice
         * @Description 检查是否在队伍中,和是否是队长
         *              inTeam 为false时，不允许当前队伍在任何队伍中，否则抛出 AlreadyInTeamException
         *              inTeam 为true时，当前用户必须在队伍中，否则抛出NoTeamException
         *              isLeader 为true时，当前用户必须为队长，否则抛出PermissionDeniedException
         *              isLeader 为false时，当前用户不允许为队长，否则抛出PermissionDeniedException
         * @Date 2020/12/10 8:55 下午
         * @Param [inTeam, needLeader]
         * @return void
         */
        User currentUser = AuthInterceptor.getCurrentUser();
        if (inTeam) {
            if (currentUser.getTeamId() == -1) throw new NoTeamException();
        } else if (currentUser.getTeamId() != -1) throw new AlreadyInTeamException();
        if (isLeader) {
            QueryWrapper<Team> qw = new QueryWrapper<>();
            qw.eq("leader_id", currentUser.getId());
            if (teamMapper.selectList(qw).size() == 0) throw new PermissionDeniedException();
        } else {
            QueryWrapper<Team> qw = new QueryWrapper<>();
            qw.eq("leader_id", currentUser.getId());
            if (teamMapper.selectList(qw).size() != 0) throw new PermissionDeniedException();
        }
    }

    @Override
    public void delMember(Integer userId) throws NoTeamException, PermissionDeniedException {
        /*
        * @Author icer
        * @Description 从队伍中删除队员
        * @Date 2020/12/9 23:49
        * @Param [userId]
        * @return void
        */
        checkCurrentUserTeam(true, true);
        //检查该用户是否是自己
        if (userId.equals(AuthInterceptor.getCurrentUserId())) throw new PermissionDeniedException();
        //检查目标用户是否和当前用户在同一队伍内
        if (!userMapper.selectById(userId).getTeamId().equals(AuthInterceptor.getCurrentUser().getTeamId()))
            throw new PermissionDeniedException();
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", userId);
        User user = new User();
        user.setTeamId(-1);
        userMapper.update(user, userUpdateWrapper);
    }

    @Override
    public List<User> getAllTeamMember(Integer teamId) throws NoTeamException{
        /*
        * @Author icer
        * @Description 获取队伍中所有成员列表，传入参数为队伍id，若传入teamId为null，则默认查询当前用户所在队伍
        * @Date 2020/12/10 16:13
        * @Param [teamId]
        * @return java.util.List<com.seedcup.seedcupbackend.common.po.User>
        */
        checkCurrentUserTeam(true, false);

        if (teamId == null) teamId = AuthInterceptor.getCurrentUser().getTeamId();

        QueryWrapper<User> uqw = new QueryWrapper<>();
        uqw.eq("team_id", teamId);
        return userMapper.selectList(uqw);
    }
}
