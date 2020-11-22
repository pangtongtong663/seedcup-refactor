package com.seedcup.seedcupbackend.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.seedcupbackend.common.exception.DuplicateUserInfoException;
import com.seedcup.seedcupbackend.common.dao.UserMapper;
import com.seedcup.seedcupbackend.common.po.User;
import com.seedcup.seedcupbackend.common.dto.UserSignUpDto;
import com.seedcup.seedcupbackend.common.service.UserService;
import com.seedcup.seedcupbackend.utils.SecurityTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void signUp(UserSignUpDto signUpDto) throws DuplicateUserInfoException {
        /*
         * @Author holdice
         * @Description 提供注册服务，会进行判重处理，判重字段为[username, phoneNumber, email]，
         * 如果有重复，会构建一个包含重复字段名list的自定义异常并抛出，由控制器处理异常。
         * @Date 2020/11/21 1:30 下午
         * @Param [signUpDto]
         * @return boolean
         * @throws com.seedcup.seedcupbackend.common.exception.DuplicateUserInfoException
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
        qw.eq("phone_number", signUpDto.getPhoneNumber());
        if (userMapper.selectList(qw).size() != 0) {
            e.addDuplicateInfoField("phoneNumber");
        }
        if (e.getDuplicateInfos().size() == 0) {
            User newUser = User.builder()
                    .username(signUpDto.getUsername())
                    .passwordMd5(SecurityTool.encrypt(signUpDto.getPassword(), signUpDto.getUsername()))
                    .phoneNumber(signUpDto.getPhoneNumber())
                    .school(signUpDto.getSchool())
                    .college(signUpDto.getCollege())
                    .className(signUpDto.getClassName())
                    .email(signUpDto.getEmail())
                    .createdTime(LocalDateTime.now())
                    .teamId(-1)
                    .build();
            userMapper.insert(newUser);
            log.info(newUser.toString());
        }
        else {
            throw e;
        }
    }

    @Override
    public boolean logIn(String username, String password) {
        //TODO 登录接口
        return false;
    }

    @Override
    public void logOut() {
        //TODO 退出登录接口
    }

    @Override
    public void editProfile() {
        //TODO 修改用户属性接口
    }
}