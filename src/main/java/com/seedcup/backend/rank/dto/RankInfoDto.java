package com.seedcup.backend.rank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RankInfoDto {

    private Integer teamId;

    private String teamName;

    private Double finalScore;
}
