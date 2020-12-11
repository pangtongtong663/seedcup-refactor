package com.seedcup.seedcupbackend.common.service;

import com.seedcup.seedcupbackend.common.dto.TeamUpdateDto;
import com.seedcup.seedcupbackend.common.dto.TeamCreateDto;
import com.seedcup.seedcupbackend.common.exception.AlreadyInTeamException;
import com.seedcup.seedcupbackend.common.exception.DuplicateInfoException;
import com.seedcup.seedcupbackend.common.exception.NoTeamException;
import com.seedcup.seedcupbackend.common.exception.PermissionDeniedException;
import com.seedcup.seedcupbackend.common.po.User;

import java.util.List;

public interface TeamService {

    void createTeam(TeamCreateDto teamCreateDto) throws DuplicateInfoException, AlreadyInTeamException;

    void editTeamInfo(TeamUpdateDto editIntroductionDtoDto) throws NoTeamException, PermissionDeniedException;

    void addMember(Integer userId) throws NoTeamException, PermissionDeniedException;

    void delMember(Integer userId) throws NoTeamException, PermissionDeniedException;

    List<User> getAllTeamMember(Integer teamId);

}
