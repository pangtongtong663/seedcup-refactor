package com.seedcup.seedcupbackend.common.service;

import com.seedcup.seedcupbackend.common.pojo.UserSignUpDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public boolean signUp(UserSignUpDto signUpDto);

    public boolean logIn(String username, String password);

    public void logOut();

    public void editProfile();
}