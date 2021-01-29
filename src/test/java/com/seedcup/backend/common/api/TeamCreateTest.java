package com.seedcup.backend.common.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.backend.ApiUtils;
import com.seedcup.backend.common.dao.TeamMapper;
import com.seedcup.backend.common.dao.UserMapper;
import com.seedcup.backend.common.po.Team;
import com.seedcup.backend.common.po.User;
import com.seedcup.backend.common.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamCreateTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void create1() throws Exception {
        /*
         * @Author icer
         * @Description 队伍创建正常测试
         * @Date 2020/12/10 9:06 下午
         * @Param []
         * @return void
         */

        userService.generateTestUser("test01", "123456");
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "  \"username\": \"test01@test.com\",\n" +
                        "  \"password\": \"123456\"\n" +
                        "}");
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"))
                .andReturn();
        request = ApiUtils.postBuilder("/api/team/create")
                .content("{\n" +
                        "  \"teamName\": \"test111\",\n" +
                        "  \"highestGrade\": \"2018\",\n" +
                        "  \"introduction\": \"111\"\n" +
                        "}")
                .cookie(result.getResponse().getCookies()[0]);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));
    }

    @Test
    public void create2() throws Exception {
        /*
         * @Author holdice
         * @Description 测试已有队伍后进行队伍创建
         * @Date 2020/12/10 9:13 下午
         * @Param []
         * @return void
         */
        userService.generateTestUser("test02", "123456");
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "  \"username\": \"test02@test.com\",\n" +
                        "  \"password\": \"123456\"\n" +
                        "}");
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"))
                .andReturn();
        var cookie = result.getResponse().getCookies()[0];
        request = ApiUtils.postBuilder("/api/team/create")
                .content("{\n" +
                        "  \"teamName\": \"test111\",\n" +
                        "  \"highestGrade\": \"2018\",\n" +
                        "  \"introduction\": \"111\"\n" +
                        "}")
                .cookie(cookie);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));
        request = ApiUtils.postBuilder("/api/team/create")
                .content("{\n" +
                        "  \"teamName\": \"test222\",\n" +
                        "  \"highestGrade\": \"2018\",\n" +
                        "  \"introduction\": \"222\"\n" +
                        "}")
                .cookie(cookie);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("109"));
    }

    @BeforeEach
    @AfterEach
    public void clearDB(){
        QueryWrapper<Team> qw = new QueryWrapper<>();
        qw.like("name", "test");
        QueryWrapper<User> uqw = new QueryWrapper<>();
        uqw.like("username", "test");
        System.out.println("after test, " + (userMapper.delete(uqw) + teamMapper.delete(qw)) + " record(s) has been deleted");
    }
}

