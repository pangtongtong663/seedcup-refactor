package com.seedcup.seedcupbackend.common.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@ApiModel(value = "队伍注册入参")
public class TeamSignUpDto {

    @NotBlank(message = "Empty teamName")
    @ApiModelProperty(value = "队伍名", required = true, notes = "不能重复", example = "小虎队")
    private String teamName;

    @NotBlank(message = "Empty highestGrade")
    @ApiModelProperty(value = "队伍最高年级", required = true, notes = "队伍最高年级", example = "2019级")
    private Integer highestGrade;

    @NotBlank(message = "Empty introduction")
    @ApiModelProperty(value = "队伍介绍", required = true, notes = "默认为空", example = "我是队伍介绍")
    private String introduction;

}
