package com.seedcup.seedcupbackend.global.dto;

import java.util.ArrayList;

public class StandardResponse {

    public static ResponseDto<Object> ok() {
        return new ResponseDto<Object>("0", "success");
    }

    public static ResponseDto<Object> fail() {
        return new ResponseDto<Object>("100", "failed");
    }

    public static ResponseDto<Object> unknown() {
        return new ResponseDto<Object>("99", "unknown error");
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

    public static ResponseDto<Object> userNotExist() {
        return new ResponseDto<>("998", "user not exist");
    }
}
