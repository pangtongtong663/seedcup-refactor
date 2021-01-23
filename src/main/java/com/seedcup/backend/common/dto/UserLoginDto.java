package com.seedcup.backend.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserLoginDto {

    @NotBlank(message = "Empty username")
    private String username;

    @NotBlank(message = "Empty password")
    private String password;
}
