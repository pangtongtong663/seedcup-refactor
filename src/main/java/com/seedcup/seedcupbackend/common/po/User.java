package com.seedcup.seedcupbackend.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password_md5")
    @JsonIgnore
    private String passwordMd5;

    @TableField(value = "phone_number")
    private String phoneNumber;

    @TableField(value = "school")
    private String school;

    @TableField(value = "college")
    private String college;

    @TableField(value = "class_name")
    private String className;

    @TableField(value = "email")
    private String email;

    @TableField(value = "created_time")
    private LocalDateTime createdTime;

    @TableField(value = "team_id")
    private Integer teamId;

    @TableField(value = "is_admin")
    private boolean isAdmin;

    private void setId(Integer id) {
        this.id = id;
    }
}
