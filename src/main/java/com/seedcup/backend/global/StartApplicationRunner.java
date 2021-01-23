package com.seedcup.backend.global;

import com.seedcup.backend.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.generateAdminUser("admin01", "admin01");
        userService.generateAdminUser("admin02", "admin02");
        userService.generateAdminUser("admin03", "admin03");
    }
}
