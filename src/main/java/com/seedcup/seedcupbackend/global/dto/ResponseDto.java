package com.seedcup.seedcupbackend.global.dto;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ApiModel(value = "标准返回出参")
public class ResponseDto<T> {

    @NonNull
    @ApiModelProperty(value = "状态码", example = "0")
    private String code;

    @NonNull
    @ApiModelProperty(value = "信息", example = "success")
    private String message;

    @ApiModelProperty(value = "数据")
    private T data;

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
