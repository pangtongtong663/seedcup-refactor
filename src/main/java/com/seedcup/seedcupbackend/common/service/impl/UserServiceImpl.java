package com.seedcup.seedcupbackend.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.seedcupbackend.common.dto.UserLoginDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateUserInfoException;
import com.seedcup.seedcupbackend.common.dao.UserMapper;
import com.seedcup.seedcupbackend.common.interceptor.AuthInterceptor;
import com.seedcup.seedcupbackend.common.po.User;
import com.seedcup.seedcupbackend.common.dto.UserSignUpDto;
import com.seedcup.seedcupbackend.common.service.UserService;
import com.seedcup.seedcupbackend.utils.SecurityTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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
                    .passwordMd5(SecurityTool.encrypt(signUpDto.getPassword(), signUpDto.getEmail()))
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
    public User logIn(UserLoginDto loginInfo) {
        String usernameOrEmailOrPhoneNumber = loginInfo.getUsername();
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", usernameOrEmailOrPhoneNumber);
        qw.or();
        qw.eq("email", usernameOrEmailOrPhoneNumber);
        qw.or();
        qw.eq("phone_number", usernameOrEmailOrPhoneNumber);
        for (User user : userMapper.selectList(qw)
             ) {
            if (SecurityTool.match(user.getPasswordMd5(), loginInfo.getPassword(), user.getEmail())) {
                user.setPasswordMd5("");
                return user;
            }
        }
        return null;
    }

    @Override
    public void logOut(HttpSession session) {
        session.removeAttribute("userInfo");
    }

    @Override
    public void editProfile() {
        //TODO 修改用户属性接口
    }

    @Override
    public void generateAdminUser(String username, String password) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("email", username + "@admin.com");
        User user = userMapper.selectOne(qw);
        if (user == null) {
            userMapper.insert(
                    User.builder()
                    .username(username).className("").college("").email(username + "@admin.com").phoneNumber("12345678900").school("")
                    .passwordMd5(SecurityTool.encrypt(password,username + "@admin.com"))
                    .createdTime(LocalDateTime.now())
                    .teamId(-1)
                    .isAdmin(true)
                    .build()
            );
            log.info("admin: " + username + " password: " + password + " generated");
        }
    }

    @Override
    public User getCurrentUser() {
        return AuthInterceptor.getCurrentUser();
    }

    @Override
    public User getUserById(Integer id) {
        User user = userMapper.selectById(id);
        user.setPasswordMd5("");
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", username);
        User user = userMapper.selectOne(qw);
        return user;
    }
}
