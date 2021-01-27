package com.seedcup.backend.rank.service;

import com.seedcup.backend.rank.dto.CommitCreateDto;
import com.seedcup.backend.rank.dto.CommitResultDto;
import com.seedcup.backend.rank.dto.RankInfoDto;

import java.util.List;

public interface RankService {

    void commit(CommitCreateDto commitCreateDto);

    List<CommitResultDto> getResult();

    List<RankInfoDto> getRankList(Integer gameStatus);
}
