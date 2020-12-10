package com.seedcup.seedcupbackend.common.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.seedcupbackend.ApiUtils;
import com.seedcup.seedcupbackend.common.dao.TeamMapper;
import com.seedcup.seedcupbackend.common.dao.UserMapper;
import com.seedcup.seedcupbackend.common.po.Team;
import com.seedcup.seedcupbackend.common.po.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamAddMemberTest {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void addMember1() throws Exception{
        var request = ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                        "  \"username\":\"test1\",\n" +
                        "  \"password\":\"12345678\",\n" +
                        "  \"school\":\"hust\",\n" +
                        "  \"college\":\"eic\",\n" +
                        "  \"className\":\"seedClass\",\n" +
                        "  \"phoneNumber\":\"15000000000\",\n" +
                        "  \"email\":\"12345678@qq.com\"\n" +
                        "}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));

        request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "  \"username\": \"admin01@admin.com\",\n" +
                        "  \"password\": \"admin01\"\n" +
                        "}");
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andReturn();

        request = ApiUtils.postBuilder("/api/team/sign_up")
                .content("{\n" +
                        "  \"teamName\": \"test111\",\n" +
                        "  \"highestGrade\": \"2018\",\n" +
                        "  \"introduction\": \"111\"\n" +
                        "}")
                .cookie(result.getResponse().getCookies()[0]);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));

        request = ApiUtils.getBuilder("/api/user/search?keyword=test1")
                .cookie(result.getResponse().getCookies()[0]);
        result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andReturn();
        String userId = result.getResponse().getContentAsString();
        System.out.println(userId);

//        request = ApiUtils.postBuilder("/api/team/addmember/");
    }

    @BeforeEach
    public void clearDB(){
        QueryWrapper<Team> qw = new QueryWrapper<>();
        qw.like("name", "test");
        System.out.println("after test, " + teamMapper.delete(qw) + "record(s) has been deleted");
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("username", "test");
        System.out.println("after test, " + userMapper.delete(userQueryWrapper) + "record(s) has been deleted");
    }
}
