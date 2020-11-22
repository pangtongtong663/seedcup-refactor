package com.seedcup.seedcupbackend.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@ApiModel(value = "用户注册入参")
public class UserSignUpDto {

    @NotBlank(message = "Empty username")
    @ApiModelProperty(value = "用户名", required = true, notes = "不能重复")
    private String username;

    @NotBlank(message = "Empty password")
    @Length(min = 6, message = "Password length less than 6")
    @ApiModelProperty(value = "密码", required = true, notes = "至少6位")
    private String password;

    @NotBlank(message = "Empty school")
    @ApiModelProperty(value = "所属学校", required = true, notes = "所属学校全称")
    private String school;

    @NotBlank(message = "Empty college")
    @ApiModelProperty(value = "所属院系", required = true, notes = "所属院系全称")
    private String college;

    @NotBlank(message = "Empty className")
    @ApiModelProperty(value = "所属班级", required = true, notes = "所属班级全称")
    private String className;

    @NotBlank(message = "Empty phoneNumber")
    @Length(min = 11, max = 11, message = "Wrong format of phone number")
    @Pattern(regexp = "^1([38][0-9]|4[579]|5[^4]|6[6]|7[0135678]|9[89])\\d{8}$"
            , message = "Wrong format of phone number")
    @ApiModelProperty(value = "电话号码", required = true, notes = "不能重复")
    private String phoneNumber;

    @NotBlank
    @Email(message = "Wrong format of email")
    @ApiModelProperty(value = "电子邮箱", required = true, notes = "不能重复")
    private String email;
}
