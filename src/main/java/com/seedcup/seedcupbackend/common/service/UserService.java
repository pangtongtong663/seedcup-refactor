package com.seedcup.seedcupbackend.common.service;

import com.seedcup.seedcupbackend.common.dto.UserSignUpDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateUserInfoException;

public interface UserService {

    void signUp(UserSignUpDto signUpDto) throws DuplicateUserInfoException;

    boolean logIn(String username, String password);

    void logOut();

    void editProfile();
}