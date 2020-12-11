package com.seedcup.seedcupbackend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
* @ClassName: UserBasicInfo
* @Description: 用来存储在session中的用户基本信息，用作维持登录状态用
* @author holdice
* @date 2020/12/11 3:23 下午
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicInfo implements Serializable {

    private Integer userId;

    private Boolean isAdmin;
}
