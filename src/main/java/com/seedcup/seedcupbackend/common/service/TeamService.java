package com.seedcup.seedcupbackend.common.service;

import com.seedcup.seedcupbackend.common.dto.TeamEditIntroductionDto;
import com.seedcup.seedcupbackend.common.dto.TeamSignUpDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateTeamInfoException;

public interface TeamService {

    void signUp(TeamSignUpDto signUpDto) throws DuplicateTeamInfoException;

    void editIntroduction(TeamEditIntroductionDto editIntroductionDtoDto);

    void addMember(String usernameOrPhoneNumberOrEmail);

}
