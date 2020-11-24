package com.seedcup.seedcupbackend.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@ApiModel(value = "用户登录入参")
public class UserLoginDto {

    @NotBlank(message = "Empty username")
    @ApiModelProperty(value = "登录用户名", required = true, notes = "可以为用户名、电话号码、邮件", example = "我是一个用户名")
    private String username;

    @NotBlank(message = "Empty password")
    @ApiModelProperty(value = "登录密码", required = true, example = "至少六位密码")
    private String password;
}
