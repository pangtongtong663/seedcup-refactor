package com.seedcup.backend.common.api;

import com.seedcup.backend.ApiUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author holdice
 * @ClassName: UserLoginTest
 * @Description: 测试登录系统功能
 * @date 2020/12/9 4:36 下午
 */

@AutoConfigureMockMvc
@SpringBootTest
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"))
                .andReturn();
        request = ApiUtils.postBuilder("/api/user/log_out");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("104"));
        request = ApiUtils.postBuilder("/api/user/log_out")
                .cookie(result.getResponse().getCookies()[0]);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("-1"))
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("102"));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));
    }

    @Test
    public void getMyInfo() throws Exception {
        /*
         * @Author holdice
         * @Description 测试用户登录后查看自己信息
         * @Date 2020/12/9 7:25 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.postBuilder("/api/user/log_in")
                .content("{\n" +
                        "  \"username\": \"admin01@admin.com\",\n" +
                        "  \"password\": \"admin01\"\n" +
                        "}");
        var result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"))
                .andReturn();

        request = ApiUtils.getBuilder("/api/user/my_info")
                .cookie(result.getResponse().getCookies()[0]);
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("admin01"));
    }
}
