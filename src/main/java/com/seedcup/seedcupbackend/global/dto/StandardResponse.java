package com.seedcup.seedcupbackend.global.dto;

import java.util.ArrayList;

public class StandardResponse {

    public static ResponseDto<Object> ok() {
        return new ResponseDto<>("0", "success");
    }

    public static ResponseDto<Object> ok(Object data) {
        return new ResponseDto<>("0", "success", data);
    }

    public static ResponseDto<Object> fail() {
        return new ResponseDto<>("100", "failed");
    }

    public static ResponseDto<Object> unknown() {
        return new ResponseDto<>("99", "unknown error");
    }

    public static ResponseDto<Object> duplicateInformation(ArrayList<String> duplicateInfos) {
        return new ResponseDto<>("101", "duplicate information", duplicateInfos);
    }

    public static ResponseDto<String> valueInvalid(String invalidInfo) {
        return new ResponseDto<>("103", "value invalid", invalidInfo);
    }

    public static ResponseDto<String> wrongHttpMethod() {
        return new ResponseDto<>("104", "http method error");
    }

    public static ResponseDto<String> notLogin() {
        return new ResponseDto<>("107", "need login");
    }

    public static ResponseDto<Object> captchaError() {
        return new ResponseDto<>("109", "verification code error");
    }

    public static ResponseDto<String> permissionDenied() {
        return new ResponseDto<>("999", "permission denied");
    }

    public static ResponseDto<Object> userNotExist() {
        return new ResponseDto<>("998", "user not exist");
    }

    public static ResponseDto<Object> applyCaptchaTooFrequently() {
        return new ResponseDto<>("997", "apply captcha too frequently");
    }

    public static ResponseDto<Object> alreadyInTeam() {
        return new ResponseDto<>("996", "already in team");
    }

    public static ResponseDto<Object> notInTeam() {
        return new ResponseDto<>("995", "not yet in team");
    }
}
