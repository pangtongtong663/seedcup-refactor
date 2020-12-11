package com.seedcup.seedcupbackend.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.seedcupbackend.common.dto.UserLoginDto;
import com.seedcup.seedcupbackend.common.exception.DuplicateInfoException;
import com.seedcup.seedcupbackend.common.dao.UserMapper;
import com.seedcup.seedcupbackend.common.exception.UnAuthException;
import com.seedcup.seedcupbackend.common.interceptor.AuthInterceptor;
import com.seedcup.seedcupbackend.common.po.User;
import com.seedcup.seedcupbackend.common.dto.UserSignUpDto;
import com.seedcup.seedcupbackend.common.service.UserService;
import com.seedcup.seedcupbackend.global.exception.SmsCaptchaWrongException;
import com.seedcup.seedcupbackend.global.service.SmsService;
import com.seedcup.seedcupbackend.utils.SecurityTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SmsService smsService;

    @Override
    public void signUp(UserSignUpDto signUpDto) throws DuplicateInfoException, SmsCaptchaWrongException {
        /*
         * @Author holdice
         * @Description 提供注册服务，首先检查验证码,会进行判重处理，判重字段为[username, phoneNumber, email]，
         * 如果有重复，会构建一个包含重复字段名list的自定义异常并抛出，由控制器处理异常。c
         * @Date 2020/11/21 1:30 下午
         * @Param [signUpDto]
         * @return boolean
         * @throws com.seedcup.seedcupbackend.common.exception.DuplicateUserInfoException
         */
        if (! smsService.checkSmsCode(signUpDto.getPhoneNumber(), signUpDto.getSmsCaptcha())) throw new SmsCaptchaWrongException();

        DuplicateInfoException e = new DuplicateInfoException();
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
        /*
         * @Author holdice
         * @Description 提供登录服务，介于用户名（真名）可能重名，故只支持邮箱和电话号码登录
         *              登录状态的维持基于session，session信息存储在redis
         * @Date 2020/12/7 9:08 下午
         * @Param [loginInfo]
         * @return com.seedcup.seedcupbackend.common.po.User
         */
        String emailOrPhoneNumber = loginInfo.getUsername();
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("email", emailOrPhoneNumber);
        qw.or();
        qw.eq("phone_number", emailOrPhoneNumber);
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
        /*
         * @Author holdice
         * @Description 提供注销登录服务，从session中删除userInfo信息
         * @Date 2020/12/7 9:09 下午
         * @Param [session]
         * @return void
         */
        if (AuthInterceptor.getCurrentUser() == null) throw new UnAuthException();
        session.removeAttribute("userInfo");
    }

    @Override
    public void editProfile() {
        //TODO 修改用户属性接口
    }

    @Override
    public void generateAdminUser(String username, String password) {
        /*
         * @Author holdice
         * @Description 生成管理员，只在应用启动时可能被调用，如果没有对应的管理员，则通过传入的username创建管理员
         * @Date 2020/12/7 9:12 下午
         * @Param [username, password]
         * @return void
         */
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("email", username + "@admin.com");
        User user = userMapper.selectOne(qw);
        if (user == null) {
            userMapper.insert(
                    User.builder()
                    .username(username).className("").college("").email(username + "@admin.com").phoneNumber("123456789" + username.substring(username.length() - 2)).school("")
                    .passwordMd5(SecurityTool.encrypt(password,username + "@admin.com"))
                    .createdTime(LocalDateTime.now())
                    .teamId(-1)
                    .isAdmin(true)
                    .build()
            );
            log.info("admin: " + username + "@admin.com" + ";password: " + password + "; generated");
        }
    }

    @Override
    public List<User> searchUser(@NotBlank String keyword) {
        /*
         * @Author holdice
         * @Description 通过关键词搜索用户
         * @Date 2020/12/11 4:00 下午
         * @Param [keyword]
         * @return java.util.List<com.seedcup.seedcupbackend.common.po.User>
         */
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like("username", keyword)
                .or()
                .like("phone_number", keyword)
                .or()
                .like("email", keyword);
        List<User> results = userMapper.selectList(qw);
        results.removeIf(User::getIsAdmin);
        return results;
    }

    @Override
    public User getCurrentUser() {
        return AuthInterceptor.getCurrentUser();
    }

    @Override
    public List<User> getAllUsers() {
        /*
         * @Author holdice
         * @Description 只有管理员有权利调用
         * @Date 2020/12/11 4:01 下午
         * @Param []
         * @return java.util.List<com.seedcup.seedcupbackend.common.po.User>
         */
        return userMapper.selectList(null);
    }

    @Override
    public void generateTestUser(String username, String password) {
        /*
         * @Author holdice
         * @Description 生成测试用户，测试使用,使用 username + @test.com 登录
         * @Date 2020/12/10 5:42 下午
         * @Param [username, password]
         * @return void
         */
        User testUser = User.builder().username(username).passwordMd5(SecurityTool.encrypt(password, username + "@test.com"))
                .phoneNumber("123456789" + new Random().nextInt(89) + 10).className("test class").college("test co")
                .createdTime(LocalDateTime.now()).email(username + "@test.com").isAdmin(false).school("test sc").teamId(-1)
                .build();
        userMapper.insert(testUser);
    }
}
