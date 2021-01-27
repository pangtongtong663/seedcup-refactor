package com.seedcup.backend.rank.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Commit {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "score")
    private Double score;

    @TableField(value = "game_status")
    private Integer gameStatus;

    @TableField(value = "file_path")
    private String filePath;

    @TableField(value = "team_id")
    private Integer teamId;

    @TableField(value = "mark_time")
    private LocalDateTime markTime;

    @TableField(value = "commit_time")
    private LocalDateTime commitTime;
}
