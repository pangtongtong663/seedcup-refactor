package com.seedcup.seedcupbackend.common.pojo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class User {
    private Long id;
    private String username;
    private String passwordMd5;
    private String phoneNumber;
    private String school;
    private String college;
    private String className;
    private String email;
    private LocalDateTime createdTime;
    private Long teamId;
}
