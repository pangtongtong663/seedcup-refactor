package com.seedcup.seedcupbackend.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String passwordMd5;
    private String phoneNumber;
    private String school;
    private String college;
    private String className;
    private String email;
    private LocalDateTime createdTime;
    private Integer teamId;

    private void setId(Integer id) {
        this.id = id;
    }
}
