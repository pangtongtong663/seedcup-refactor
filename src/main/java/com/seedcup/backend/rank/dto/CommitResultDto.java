package com.seedcup.backend.rank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CommitResultDto {

    private Integer id;

    private Integer gameStatus;

    private Double score;

    private LocalDateTime markTime;

    private LocalDateTime commitTime;
}
