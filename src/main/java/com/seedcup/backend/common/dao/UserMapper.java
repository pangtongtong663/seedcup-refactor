package com.seedcup.backend.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seedcup.backend.common.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
