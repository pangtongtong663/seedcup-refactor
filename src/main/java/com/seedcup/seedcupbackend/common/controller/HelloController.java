package com.seedcup.seedcupbackend.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.seedcupbackend.common.pojo.User;
import com.seedcup.seedcupbackend.common.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {
        /**
         * @Author holdice
         * @Description
         * @Date 2020/11/20 11:45 下午
         * @Param java.lang.String
         * @return java.lang.String
         **/
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("id", 1).eq("userName", "hhh");
        List<User> users = userMapper.selectList(qw);
        return users.toString();
    }
}
