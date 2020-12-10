package com.seedcup.seedcupbackend.common.service;

import com.seedcup.seedcupbackend.common.dto.UserLoginDto;
import com.seedcup.seedcupbackend.common.dto.UserSignUpDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateInfoException;
import com.seedcup.seedcupbackend.common.po.User;
import com.seedcup.seedcupbackend.global.exception.SmsCaptchaWrongException;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    void signUp(UserSignUpDto signUpDto) throws DuplicateInfoException, SmsCaptchaWrongException;

    User logIn(UserLoginDto loginInfo);

    void logOut(HttpSession session);

    void editProfile();

    void generateAdminUser(String username, String password);

    List<User> searchUser(String keyword);

    User getCurrentUser();

    List<User> getAllUsers();

    void generateTestUser(String username, String password);
}