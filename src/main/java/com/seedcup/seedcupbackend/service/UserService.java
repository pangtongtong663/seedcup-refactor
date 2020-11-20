package com.seedcup.seedcupbackend.service;

import com.seedcup.seedcupbackend.common.pojo.User;
import com.seedcup.seedcupbackend.common.pojo.UserSignUpDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public boolean signUp(UserSignUpDto signUpDto);

    public boolean logIn(String username, String password);
}