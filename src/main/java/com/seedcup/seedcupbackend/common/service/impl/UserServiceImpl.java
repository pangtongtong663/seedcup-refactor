package com.seedcup.seedcupbackend.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.seedcupbackend.common.exception.DuplicateUserInfoException;
import com.seedcup.seedcupbackend.common.mapper.UserMapper;
import com.seedcup.seedcupbackend.common.pojo.User;
import com.seedcup.seedcupbackend.common.pojo.UserSignUpDto;
import com.seedcup.seedcupbackend.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void signUp(UserSignUpDto signUpDto) throws DuplicateUserInfoException {
        /*
         * @Author holdice
         * @Description 提供注册服务
         * @Date 2020/11/21 1:30 下午
         * @Param [signUpDto]
         * @return boolean
         * @throws DuplicateUserInfoException
         */
        DuplicateUserInfoException e = new DuplicateUserInfoException();
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", signUpDto.getUsername());
        if (userMapper.selectList(qw).size() != 0) {
            e.addDuplicateInfoField("username");
        }
        qw.clear();
        qw.eq("email", signUpDto.getEmail());
        if (userMapper.selectList(qw).size() != 0) {
            e.addDuplicateInfoField("email");
        }
        qw.clear();
        qw.eq("phoneNumber", signUpDto.getPhoneNumber());
        if (userMapper.selectList(qw).size() != 0) {
            e.addDuplicateInfoField("phoneNumber");
        }
        if (e.getDuplicateInfos().size() == 0) {
            //TODO 新建用户到数据库
        }
    }

    @Override
    public boolean logIn(String username, String password) {
        return false;
    }

    @Override
    public void logOut() {

    }

    @Override
    public void editProfile() {

    }
}
