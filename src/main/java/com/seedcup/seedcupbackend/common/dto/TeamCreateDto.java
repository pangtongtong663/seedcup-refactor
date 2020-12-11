package com.seedcup.seedcupbackend.common.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class TeamCreateDto {

    @NotBlank(message = "Empty teamName")
    private String teamName;

    @NotBlank(message = "Empty highestGrade")
    private Integer highestGrade;

    @NotBlank(message = "Empty introduction")
    private String introduction;

}
