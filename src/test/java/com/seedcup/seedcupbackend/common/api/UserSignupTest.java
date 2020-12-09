package com.seedcup.seedcupbackend.common.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.seedcupbackend.ApiUtils;
import com.seedcup.seedcupbackend.common.dao.UserMapper;
import com.seedcup.seedcupbackend.common.po.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
* @ClassName: UserSignupTest
* @Description: 用户注册系统测试
* @author holdice
* @date 2020/12/9 4:41 下午
*/

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class UserSignupTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void signup1() throws Exception {
        /*
         * @Author holdice
         * @Description 测试正常注册
         * @Date 2020/12/9 1:53 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                        "    \"username\": \"test1\",\n" +
                        "    \"password\": \"123456\",\n" +
                        "    \"school\": \"hust\",\n" +
                        "    \"college\": \"eic\",\n" +
                        "    \"className\": \"testclass1801\",\n" +
                        "    \"phoneNumber\": \"18707116948\",\n" +
                        "    \"email\": \"hemu0710@test.com\"\n" +
                        "}");
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));

    }

    @Test
    public void signup2() throws Exception {
        /*
         * @Author holdice
         * @Description 测试非法输入，错误电话号码
         * @Date 2020/12/9 1:54 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                        "  \"username\": \"test2\",\n" +
                        "  \"password\": \"123456\",\n" +
                        "  \"school\": \"hit\",\n" +
                        "  \"college\": \"cs\",\n" +
                        "  \"className\": \"bala8801\",\n" +
                        "  \"phoneNumber\": \"12345678\",\n" +
                        "  \"email\": \"123@123.com\"\n" +
                        "}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("103"));
    }

    @Test
    public void signup3() throws Exception {
        /*
         * @Author holdice
         * @Description 测试非法输入，密码过短
         * @Date 2020/12/9 3:31 下午
         * @Param []
         * @return void
         */
        var request =ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                    "  \"username\": \"test3\",\n" +
                    "  \"password\": \"123\",\n" +
                    "  \"school\": \"hist\",\n" +
                    "  \"college\": \"cs\",\n" +
                    "  \"className\": \"sdwqe123\",\n" +
                    "  \"phoneNumber\": \"18707116946\",\n" +
                    "  \"email\": \"123@twxt.com\"\n" +
                    "}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("103"));
    }

    @Test
    public void signup4() throws Exception {
        /*
         * @Author holdice
         * @Description 测试非法输入，错误邮箱
         * @Date 2                020/12/9 3:32 下午
         * @Param []
         * @return void
         */
        var request =ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                        "  \"username\": \"test4\",\n" +
                        "  \"password\": \"123456\",\n" +
                        "  \"school\": \"hist\",\n" +
                        "  \"college\": \"cs\",\n" +
                        "  \"className\": \"sdwqe123\",\n" +
                        "  \"phoneNumber\": \"18707116946\",\n" +
                        "  \"email\": \"1232twxt.com\"\n" +
                        "}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("103"));
    }

    @Test
    public void signup5() throws Exception {
        /*
         * @Author holdice
         * @Description 测试重复注册信息
         * @Date 2020/12/9 3:35 下午
         * @Param []
         * @return void
         */
        var request =ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                        "  \"username\": \"test5\",\n" +
                        "  \"password\": \"123456\",\n" +
                        "  \"school\": \"hist\",\n" +
                        "  \"college\": \"cs\",\n" +
                        "  \"className\": \"sdwqe123\",\n" +
                        "  \"phoneNumber\": \"18707116946\",\n" +
                        "  \"email\": \"1232@twxt.com\"\n" +
                        "}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"));
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").value("email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1]").value("phoneNumber"));
    }

    @AfterEach
    public void clearDB() {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like("username", "test");
        System.out.println("after test, " + userMapper.delete(qw) + " record(s) was deleted");
    }
}
