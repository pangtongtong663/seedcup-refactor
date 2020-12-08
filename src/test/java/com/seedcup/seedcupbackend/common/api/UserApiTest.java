package com.seedcup.seedcupbackend.common.api;

import com.seedcup.seedcupbackend.common.controller.HelloController;
import com.seedcup.seedcupbackend.common.controller.UserController;
import com.seedcup.seedcupbackend.common.service.UserService;
import lombok.val;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpCookie;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@EnableWebMvc
@AutoConfigureMockMvc
@SpringBootTest()
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApiTest {

    @Autowired
    private MockMvc mockMvc;

    private Cookie[] cookies;

    @Test
    @Order(1)
    public void login() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/user/log_in")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\n" +
                        "    \"username\": \"admin01@admin.com\",\n" +
                        "    \"password\": \"admin01\"\n" +
                        "}");
        MvcResult result = mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        cookies = result.getResponse().getCookies();
    }

    @Test
    @Order(2)
    public void logout() throws  Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/user/log_out")
                .cookie(cookies);
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
