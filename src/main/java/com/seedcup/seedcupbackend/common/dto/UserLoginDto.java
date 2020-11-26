package com.seedcup.seedcupbackend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserLoginDto {

    @NotBlank(message = "Empty username")
    private String username;

    @NotBlank(message = "Empty password")
    private String password;
}
