package com.seedcup.seedcupbackend.common.service;

import com.seedcup.seedcupbackend.common.dto.UserLoginDto;
import com.seedcup.seedcupbackend.common.dto.UserSignUpDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateUserInfoException;
import com.seedcup.seedcupbackend.common.po.User;

public interface UserService {

    void signUp(UserSignUpDto signUpDto) throws DuplicateUserInfoException;

    User logIn(UserLoginDto loginInfo);

    void logOut();

    void editProfile();
}