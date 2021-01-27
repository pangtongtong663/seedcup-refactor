package com.seedcup.backend.global.dto;

import java.util.ArrayList;

public class StandardResponse {

    public static ResponseDto<Object> ok() {
        return new ResponseDto<>("100", "success");
    }

    public static ResponseDto<Object> ok(Object data) {
        return new ResponseDto<>("100", "success", data);
    }

    public static ResponseDto<Object> fail() {
        return new ResponseDto<>("-1", "failed");
    }

    public static ResponseDto<Object> unknown() {
        return new ResponseDto<>("99", "unknown error");
    }

    public static ResponseDto<Object> duplicateInformation(ArrayList<String> duplicateInfos) {
        return new ResponseDto<>("101", "duplicate information", duplicateInfos);
    }

    public static ResponseDto<String> valueInvalid(String invalidInfo) {
        return new ResponseDto<>("102", "value invalid", invalidInfo);
    }

    public static ResponseDto<String> wrongHttpMethod() {
        return new ResponseDto<>("103", "http method error");
    }

    public static ResponseDto<String> notLogin() {
        return new ResponseDto<>("104", "need login");
    }

    public static ResponseDto<Object> captchaError() {
        return new ResponseDto<>("105", "verification code error");
    }

    public static ResponseDto<String> permissionDenied() {
        return new ResponseDto<>("106", "permission denied");
    }

    public static ResponseDto<Object> userNotExist() {
        return new ResponseDto<>("107", "user not exist");
    }

    public static ResponseDto<Object> applyCaptchaTooFrequently() {
        return new ResponseDto<>("108", "apply captcha too frequently");
    }

    public static ResponseDto<Object> alreadyInTeam() {
        return new ResponseDto<>("109", "already in team");
    }

    public static ResponseDto<String> notInTeam() {
        return new ResponseDto<>("110", "not yet in team");
    }
}
