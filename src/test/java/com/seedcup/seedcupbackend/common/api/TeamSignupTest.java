package com.seedcup.seedcupbackend.common.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.seedcupbackend.ApiUtils;
import com.seedcup.seedcupbackend.common.dao.TeamMapper;
import com.seedcup.seedcupbackend.common.po.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamSignupTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamMapper teamMapper;

    @Test
    public void signUp1() throws Exception{
        var request = ApiUtils.postBuilder("/api/user/log_in")
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
    }

    @AfterEach
    public void clearDB(){
        QueryWrapper<Team> qw = new QueryWrapper<>();
        qw.like("name", "test");
        System.out.println("after test, " + teamMapper.delete(qw) + "record(s) has been deleted");
    }
}

