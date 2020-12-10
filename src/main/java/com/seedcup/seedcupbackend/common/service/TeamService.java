package com.seedcup.seedcupbackend.common.service;

import com.seedcup.seedcupbackend.common.dto.TeamEditIntroductionDto;
import com.seedcup.seedcupbackend.common.dto.TeamSignUpDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateInfoException;
import com.seedcup.seedcupbackend.common.po.Team;
import com.seedcup.seedcupbackend.common.po.User;

import java.util.List;

public interface TeamService {

    void signUp(TeamSignUpDto signUpDto) throws DuplicateInfoException;

    void editIntroduction(TeamEditIntroductionDto editIntroductionDtoDto);

    void addMember(Integer userId);

    void delMember(Integer userId);

    List<User> getAllTeamMember(Integer teamId);

}
