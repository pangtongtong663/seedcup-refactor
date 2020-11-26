package com.seedcup.seedcupbackend.global.dto;

import com.alibaba.fastjson.JSON;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseDto<T> {

    @NonNull
    private String code;

    @NonNull
    private String message;

    private T data;

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
