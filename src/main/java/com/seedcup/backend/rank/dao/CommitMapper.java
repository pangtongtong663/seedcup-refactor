package com.seedcup.backend.rank.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seedcup.backend.rank.po.Commit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommitMapper extends BaseMapper<Commit> {
}
