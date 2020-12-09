package com.seedcup.seedcupbackend.common.api;

import com.seedcup.seedcupbackend.ApiUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));
    }

    @Test
    public void login2() throws Exception {
        /*
         * @Author holdice
         * @Description 测试LoginRequired注释是否工作正常
         * @Date 2020/12/9 4:23 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "    \"username\": \"admin01@admin.com\",\n" +
                        "    \"password\": \"admin01\"\n" +
                        "}");
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
                .andReturn();
        request = ApiUtils.getBuilder("/api/user/log_out");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("107"));
        request = ApiUtils.getBuilder("/api/user/log_out")
                .cookie(result.getResponse().getCookies()[0]);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));
    }

    @Test
    public void login3() throws Exception {
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
    public void login4() throws Exception {
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
    public void login5() throws Exception {
        /*
         * @Author holdice
         * @Description 测试使用电话号码登录
         * @Date 2020/12/8 10:45 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "    \"username\": \"12345678901\",\n" +
                        "    \"password\": \"admin01\"\n" +
                        "}");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));
    }
}
