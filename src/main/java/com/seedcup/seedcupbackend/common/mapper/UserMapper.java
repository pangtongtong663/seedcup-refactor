package com.seedcup.seedcupbackend.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seedcup.seedcupbackend.common.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
