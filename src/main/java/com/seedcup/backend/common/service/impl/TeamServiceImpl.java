package com.seedcup.backend.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.seedcup.backend.common.dao.TeamMapper;
import com.seedcup.backend.common.dao.UserMapper;
import com.seedcup.backend.common.dto.TeamInfoDto;
import com.seedcup.backend.common.dto.TeamUpdateDto;
import com.seedcup.backend.common.dto.TeamCreateDto;
import com.seedcup.backend.common.exception.*;
import com.seedcup.backend.common.interceptor.AuthInterceptor;
import com.seedcup.backend.common.interceptor.TeamInterceptor;
import com.seedcup.backend.common.po.Team;
import com.seedcup.backend.common.po.User;
import com.seedcup.backend.common.service.TeamService;
import com.seedcup.backend.rank.dao.CommitMapper;
import com.seedcup.backend.rank.po.Commit;
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

        //判断是否已在队伍
        if (AuthInterceptor.getCurrentUser().getTeamId() != -1) throw new AlreadyInTeamException();

        //判断队名是否重复
        QueryWrapper<Team> qw = new QueryWrapper<>();
        qw.eq("name", teamCreateDto.getTeamName());
        if(teamMapper.selectList(qw).size() != 0) {
            DuplicateInfoException e = new DuplicateInfoException();
            e.addDuplicateInfoField("teamName");
            throw e;
        } else {
            //创建队伍
            Team newTeam = Team.builder()
                    .name(teamCreateDto.getTeamName())
                    .highestGrade(teamCreateDto.getHighestGrade())
                    .introduction(teamCreateDto.getIntroduction())
                    .leaderId(AuthInterceptor.getCurrentUser().getId())
                    .build();
            teamMapper.insert(newTeam);
            UpdateWrapper<User> uuw = new UpdateWrapper<>();
            uuw.eq("id", newTeam.getLeaderId());
            User tempUser = User.builder().teamId(newTeam.getId()).build();
            userMapper.update(tempUser, uuw);
            log.info(newTeam.toString());
        }
    }

    @Override
    public void editTeamInfo(TeamUpdateDto teamUpdateDto) {
        String newIntroduction = teamUpdateDto.getIntroduction();
        UpdateWrapper<Team> uw = new UpdateWrapper<>();
        uw.eq("leader_id", AuthInterceptor.getCurrentUser().getId());
        Team team = new Team();
        team.setIntroduction(newIntroduction);
        teamMapper.update(team, uw);
    }

    @Override
    public void addMember(Integer newMemberId) throws PermissionDeniedException, AlreadyInTeamException {
        //判断目标用户是否是自己
        if (newMemberId.equals(AuthInterceptor.getCurrentUserId())) throw new PermissionDeniedException();
        //判断目标是否已有队伍，如果有，抛出异常
        if (userMapper.selectById(newMemberId).getTeamId() != -1) throw new AlreadyInTeamException();
        UpdateWrapper<User> uuw = new UpdateWrapper<>();
        uuw.eq("id", newMemberId);
        User tempUser = User.builder().teamId(TeamInterceptor.getCurrentTeam().getId()).build();
        userMapper.update(tempUser, uuw);
    }

    @Override
    public void delMember(Integer deletedMemberId) throws PermissionDeniedException {
        //检查该用户是否是自己
        if (deletedMemberId.equals(AuthInterceptor.getCurrentUserId())) throw new PermissionDeniedException();
        //检查目标用户是否和当前用户在同一队伍内
        if (!userMapper.selectById(deletedMemberId).getTeamId().equals(AuthInterceptor.getCurrentUser().getTeamId()))
            throw new PermissionDeniedException();
        UpdateWrapper<User> uuw = new UpdateWrapper<>();
        uuw.eq("id", deletedMemberId);
        User tempUser = User.builder().teamId(-1).build();
        userMapper.update(tempUser, uuw);
    }

    @Override
    public TeamInfoDto getTeamInfo(Integer teamId){
        QueryWrapper<User> uqw = new QueryWrapper<>();
        uqw.eq("team_id", teamId);
        return new TeamInfoDto(teamMapper.selectById(teamId), userMapper.selectList(uqw));
    }
}
