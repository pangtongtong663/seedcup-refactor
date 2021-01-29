package com.seedcup.backend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicInfo implements Serializable {

    private Integer userId;

    private Boolean isAdmin;
}
