package com.seedcup.seedcupbackend.common.api;

import com.seedcup.seedcupbackend.ApiUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void login1() throws Exception {
        /*
         * @Author holdice
         * @Description 测试登录成功
         * @Date 2020/12/8 10:24 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "    \"username\": \"admin01@admin.com\",\n" +
                        "    \"password\": \"admin01\"\n" +
                        "}");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andReturn();
    }

    @Test
    @Order(2)
    public void login2() throws Exception {
        /*
         * @Author holdice
         * @Description 测试密码错误导致登录失败
         * @Date 2020/12/8 10:29 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "    \"username\": \"admin01@admin.com\",\n" +
                        "    \"password\": \"admin02\"\n" +
                        "}");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"))
                .andReturn();
    }

    @Test
    @Order(3)
    public void login3() throws Exception {
        /*
         * @Author holdice
         * @Description 测试非法输入
         * @Date 2020/12/8 10:29 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "    \"username\": \"\",\n" +
                        "    \"password\": \"admin02\"\n" +
                        "}");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("103"));
    }

    @Test
    @Order(5)
    public void logout() throws Exception {
        var request = ApiUtils.getBuilder("/api/user/log_out");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("107"))
                .andReturn();
    }
}
