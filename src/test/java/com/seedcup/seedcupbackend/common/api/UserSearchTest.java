package com.seedcup.seedcupbackend.common.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.seedcupbackend.ApiUtils;
import com.seedcup.seedcupbackend.common.dao.UserMapper;
import com.seedcup.seedcupbackend.common.dto.UserSignUpDto;
import com.seedcup.seedcupbackend.common.po.User;
import com.seedcup.seedcupbackend.common.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
* @ClassName: UserSearchTest
* @Description: 测试用户搜索功能
* @author holdice
* @date 2020/12/9 7:13 下午
*/

@SpringBootTest
@AutoConfigureMockMvc
public class UserSearchTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void search1() throws Exception {
        /*
         * @Author holdice
         * @Description 测试搜索用户功能
         * @Date 2020/12/9 7:04 下午
         * @Param []
         * @return void
         */
        userService.signUp(
                UserSignUpDto.builder()
                        .username("test1")
                        .password("123456")
                        .className("cs1801")
                        .college("cs")
                        .school("hust")
                        .email("test@hanser.com")
                        .phoneNumber("18734892343")
                        .build()
        );
        userService.signUp(
                UserSignUpDto.builder()
                        .username("test2")
                        .password("123456")
                        .className("eic1801")
                        .college("eic")
                        .school("hust")
                        .email("test222@biosheep.com")
                        .phoneNumber("18734892221")
                        .build()
        );
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "  \"username\": \"admin01@admin.com\",\n" +
                        "  \"password\": \"admin01\"\n" +
                        "}");
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andReturn();
        request = ApiUtils.getBuilder("/api/user/search")
                .param("keyword", "biosheep")
                .cookie(result.getResponse().getCookies()[0]);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists());
    }

    @Test
    public void search2() throws Exception {
        /*
         * @Author holdice
         * @Description 测试搜索管理员，不应该能搜索到管理员
         * @Date 2020/12/9 7:13 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "  \"username\": \"admin01@admin.com\",\n" +
                        "  \"password\": \"admin01\"\n" +
                        "}");
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andReturn();
        request = ApiUtils.getBuilder("/api/user/search")
                .param("keyword", "admin")
                .cookie(result.getResponse().getCookies()[0]);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void getAll1() throws Exception {
        /*
         * @Author holdice
         * @Description 获取所有用户list测试，需要管理员
         * @Date 2020/12/9 11:25 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "  \"username\": \"admin01@admin.com\",\n" +
                        "  \"password\": \"admin01\"\n" +
                        "}");
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andReturn();
        request = ApiUtils.getBuilder("/api/user/all")
                .cookie(result.getResponse().getCookies()[0]);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray());
    }

    @Test
    public void getAll2() throws Exception {
        /*
         * @Author holdice
         * @Description 测试非管理员用户尝试获取所有用户，应该返回permission denied
         * @Date 2020/12/9 11:33 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                        "    \"username\": \"test2\",\n" +
                        "    \"password\": \"123456\",\n" +
                        "    \"school\": \"hust\",\n" +
                        "    \"college\": \"eic\",\n" +
                        "    \"className\": \"cs1802\",\n" +
                        "    \"phoneNumber\": \"18707112324\",\n" +
                        "    \"email\": \"test@qq.com\"\n" +
                        "}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));

        request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "  \"username\": \"test@qq.com\",\n" +
                        "  \"password\": \"123456\"\n" +
                        "}");
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andReturn();
        request = ApiUtils.getBuilder("/api/user/all")
                .cookie(result.getResponse().getCookies()[0]);
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("999"));
    }

    @AfterEach
    public void clearDB(){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like("username", "test");
        System.out.println("after test, " + userMapper.delete(qw) + " record(s) was deleted");
    }
}
