package com.seedcup.seedcupbackend.common.service;

import com.seedcup.seedcupbackend.common.dto.TeamEditIntroductionDto;
import com.seedcup.seedcupbackend.common.dto.TeamSignUpDto;
import com.seedcup.seedcupbackend.common.exception.AlreadyInTeamException;
import com.seedcup.seedcupbackend.common.exception.DuplicateInfoException;
import com.seedcup.seedcupbackend.common.exception.NoTeamException;
import com.seedcup.seedcupbackend.common.exception.PermissionDeniedException;
import com.seedcup.seedcupbackend.common.po.Team;
import com.seedcup.seedcupbackend.common.po.User;

import java.util.List;

public interface TeamService {

    void signUp(TeamSignUpDto signUpDto) throws DuplicateInfoException, AlreadyInTeamException;

    void editIntroduction(TeamEditIntroductionDto editIntroductionDtoDto) throws NoTeamException, PermissionDeniedException;

    void addMember(Integer userId) throws NoTeamException, PermissionDeniedException;

    void delMember(Integer userId) throws NoTeamException, PermissionDeniedException;

    List<User> getAllTeamMember(Integer teamId);

}
