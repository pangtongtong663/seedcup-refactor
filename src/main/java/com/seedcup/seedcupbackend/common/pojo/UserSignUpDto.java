package com.seedcup.seedcupbackend.common.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignUpDto {
    private String username;
    private String password;
    private String school;
    private String college;
    private String className;
    private String email;
}
