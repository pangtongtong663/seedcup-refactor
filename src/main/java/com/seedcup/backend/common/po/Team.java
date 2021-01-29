package com.seedcup.backend.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "leader_id")
    private Integer leaderId;

    @TableField(value = "highest_grade")
    private Integer highestGrade;

    @TableField(value = "introduction")
    private String introduction;

    @TableField(value = "game_status")
    private Integer gameStatus;
}
