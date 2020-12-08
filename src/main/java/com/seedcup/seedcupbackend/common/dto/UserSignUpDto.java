package com.seedcup.seedcupbackend.common.dto;

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
public class UserSignUpDto {

    @NotBlank(message = "Empty username")
    private String username;

    @NotBlank(message = "Empty password")
    @Length(min = 6, message = "Password length less than 6")
    private String password;

    @NotBlank(message = "Empty school")
    private String school;

    @NotBlank(message = "Empty college")
    private String college;

    @NotBlank(message = "Empty class name")
    private String className;

    @NotBlank(message = "Empty phone number")
    @Length(min = 11, max = 11, message = "Wrong format of phone number")
    @Pattern(regexp = "^1([38][0-9]|4[579]|5[^4]|6[6]|7[0135678]|9[89])\\d{8}$"
            , message = "Wrong format of phone number")
    private String phoneNumber;

    @NotBlank
    @Email(message = "Wrong format of email")
    private String email;
}
