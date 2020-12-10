package com.seedcup.seedcupbackend.common.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.seedcupbackend.ApiUtils;
import com.seedcup.seedcupbackend.common.dao.TeamMapper;
import com.seedcup.seedcupbackend.common.dao.UserMapper;
import com.seedcup.seedcupbackend.common.po.Team;
import com.seedcup.seedcupbackend.common.po.User;
import com.seedcup.seedcupbackend.common.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamAddMemberTest {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void addMember1() throws Exception{
        userService.generateTestUser("test01", "123456");
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "  \"username\": \"test01@test.com\",\n" +
                        "  \"password\": \"123456\"\n" +
                        "}");
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andReturn();
        var cookie = result.getResponse().getCookies()[0];

        request = ApiUtils.postBuilder("/api/team/sign_up")
                .content("{\n" +
                        "  \"teamName\": \"test111\",\n" +
                        "  \"highestGrade\": \"2018\",\n" +
                        "  \"introduction\": \"111\"\n" +
                        "}")
                .cookie(cookie);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));

        request = ApiUtils.getBuilder("/api/user/search?keyword=test")
                .cookie(cookie);
        result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andReturn();
        JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
        System.out.println(jsonObject.getJSONArray("data").getJSONObject(0).getInteger("id"));
        request = ApiUtils.postBuilder(String.format("/api/team/member/add/%d", JSON.parseObject(result.getResponse()
                .getContentAsString()).getJSONArray("data").getJSONObject(0).getInteger("id")))
                .cookie(cookie);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));
    }

    @AfterEach
    @BeforeEach
    public void clearDB(){
        QueryWrapper<Team> qw = new QueryWrapper<>();
        qw.like("name", "test");
        System.out.println("after test, " + teamMapper.delete(qw) + "record(s) has been deleted");
        QueryWrapper<User> uqw = new QueryWrapper<>();
        uqw.like("username", "test");
        System.out.println("after test, " + userMapper.delete(uqw) + "record(s) has been deleted");
    }
}
