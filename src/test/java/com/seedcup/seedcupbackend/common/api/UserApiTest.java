package com.seedcup.seedcupbackend.common.api;

import com.seedcup.seedcupbackend.common.controller.HelloController;
import com.seedcup.seedcupbackend.common.controller.UserController;
import com.seedcup.seedcupbackend.common.service.UserService;
import lombok.val;
import org.hibernate.validator.internal.util.logging.formatter.CollectionOfClassesObjectFormatter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.SessionAttribute;
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

    private static Cookie[] cookies;

    @Test
    @Order(1)
    public void login() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/user/log_in")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
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
    public void logout() throws  Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/user/log_out");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
