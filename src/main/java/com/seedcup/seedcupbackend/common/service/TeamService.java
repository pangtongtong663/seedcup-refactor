package com.seedcup.seedcupbackend.common.service;

import com.seedcup.seedcupbackend.common.dto.TeamEditIntroductionDto;
import com.seedcup.seedcupbackend.common.dto.TeamSignUpDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateInfoException;

public interface TeamService {

    void signUp(TeamSignUpDto signUpDto) throws DuplicateInfoException;

    void editIntroduction(TeamEditIntroductionDto editIntroductionDtoDto);

    void addMember(String usernameOrPhoneNumberOrEmail);

}
