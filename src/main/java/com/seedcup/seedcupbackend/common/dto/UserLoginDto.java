package com.seedcup.seedcupbackend.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "用户登录入参")
public class UserLoginDto {

    @ApiModelProperty
    private String username;

    private String password;
}
