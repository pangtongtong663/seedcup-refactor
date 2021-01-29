package com.seedcup.backend.common.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.backend.ApiUtils;
import com.seedcup.backend.common.dao.UserMapper;
import com.seedcup.backend.common.po.User;
import com.seedcup.backend.global.service.RedisService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
* @ClassName: UserSignupTest
* @Description: 用户注册系统测试
* @author holdice
* @date 2020/12/9 4:41 下午
*/

@AutoConfigureMockMvc
@SpringBootTest
public class UserSignupTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Test
    public void signup1() throws Exception {
        /*
         * @Author holdice
         * @Description 测试正常注册
         * @Date 2020/12/9 1:53 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.getBuilder("/api/sms/send_captcha")
                .param("phoneNumber", "18707116948");

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));

        String captcha = redisService.getValue("sms:" + "18707116948");

        request = ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                        "    \"username\": \"test1\",\n" +
                        "    \"password\": \"123456\",\n" +
                        "    \"school\": \"hust\",\n" +
                        "    \"college\": \"eic\",\n" +
                        "    \"className\": \"testclass1801\",\n" +
                        "    \"phoneNumber\": \"18707116948\",\n" +
                        "    \"smsCaptcha\": " + captcha + ",\n" +
                        "    \"email\": \"hemu0710@test.com\"\n" +
                        "}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));

    }

    @Test
    public void signup2() throws Exception {
        /*
         * @Author holdice
         * @Description 测试获取验证码，错误电话号码格式
         * @Date 2020/12/9 1:54 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.getBuilder("/api/sms/send_captcha")
                .param("phoneNumber", "187071112343");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("102"));
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

        var request = ApiUtils.getBuilder("/api/sms/send_captcha")
                .param("phoneNumber", "18707116946");

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));
        String captcha = redisService.getValue("sms:" + "18707116946");

        request = ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                    "  \"username\": \"test3\",\n" +
                    "  \"password\": \"123\",\n" +
                    "  \"school\": \"hist\",\n" +
                    "  \"college\": \"cs\",\n" +
                    "  \"className\": \"sdwqe123\",\n" +
                        "  \"phoneNumber\": \"18707116946\",\n" +
                        "  \"smsCaptcha\": " + captcha + ",\n" +
                        "  \"email\": \"123@twxt.com\"\n" +
                    "}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("102"));
    }

    @Test
    public void signup4() throws Exception {
        /*
         * @Author holdice
         * @Description 测试非法输入，错误邮箱
         * @Date 2020/12/9 3:32 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.getBuilder("/api/sms/send_captcha")
                .param("phoneNumber", "18707116946");

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));
        String captcha = redisService.getValue("sms:" + "18707116946");

        request = ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                        "  \"username\": \"test3\",\n" +
                        "  \"password\": \"123123\",\n" +
                        "  \"school\": \"hist\",\n" +
                        "  \"college\": \"cs\",\n" +
                        "  \"className\": \"sdwqe123\",\n" +
                        "  \"phoneNumber\": \"18707116946\",\n" +
                        "  \"smsCaptcha\": " + captcha + ",\n" +
                        "  \"email\": \"123123.com\"\n" +
                        "}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("102"));
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
        var request = ApiUtils.getBuilder("/api/sms/send_captcha")
                .param("phoneNumber", "18707116946");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));
        String captcha = redisService.getValue("sms:" + "18707116946");

        request = ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                        "  \"username\": \"test3\",\n" +
                        "  \"password\": \"123123\",\n" +
                        "  \"school\": \"hist\",\n" +
                        "  \"college\": \"cs\",\n" +
                        "  \"className\": \"sdwqe123\",\n" +
                        "  \"phoneNumber\": \"18707116946\",\n" +
                        "  \"smsCaptcha\": " + captcha + ",\n" +
                        "  \"email\": \"123@ccs.com\"\n" +
                        "}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));

        // 重复信息注册
        request = ApiUtils.getBuilder("/api/sms/send_captcha")
                .param("phoneNumber", "18707116947");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));
        captcha = redisService.getValue("sms:" + "18707116947");

        request = ApiUtils.postBuilder("/api/user/sign_up")
                .content("{\n" +
                        "  \"username\": \"test3\",\n" +
                        "  \"password\": \"123123\",\n" +
                        "  \"school\": \"hist\",\n" +
                        "  \"college\": \"cs\",\n" +
                        "  \"className\": \"sdwqe123\",\n" +
                        "  \"phoneNumber\": \"18707116947\",\n" +
                        "  \"smsCaptcha\": " + captcha + ",\n" +
                        "  \"email\": \"123@ccs.com\"\n" +
                        "}");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("101"));

    }

    @Test
    public void signup6() throws Exception {
        /*
         * @Author holdice
         * @Description 测试过快请求验证码
         * @Date 2020/12/10 7:00 下午
         * @Param []
         * @return void
         */
        var request = ApiUtils.getBuilder("/api/sms/send_captcha")
                .param("phoneNumber", "18707116946");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("100"));
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("108"));
    }

    @AfterEach
    public void clearDB() {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like("username", "test");
        System.out.println("after test, " + userMapper.delete(qw) + " record(s) was deleted");
        redisService.clearRedis();
    }
}
