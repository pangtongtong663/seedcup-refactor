package com.seedcup.seedcupbackend.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seedcup.seedcupbackend.common.po.Team;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamMapper extends BaseMapper<Team> {

}
