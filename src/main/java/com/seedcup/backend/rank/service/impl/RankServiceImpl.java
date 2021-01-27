package com.seedcup.backend.rank.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.backend.common.dao.TeamMapper;
import com.seedcup.backend.common.po.Team;
import com.seedcup.backend.rank.dao.CommitMapper;
import com.seedcup.backend.rank.dto.CommitCreateDto;
import com.seedcup.backend.rank.dto.CommitResultDto;
import com.seedcup.backend.rank.dto.RankInfoDto;
import com.seedcup.backend.rank.po.Commit;
import com.seedcup.backend.rank.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RankServiceImpl implements RankService {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private CommitMapper commitMapper;

    @Override
    public void commit(CommitCreateDto commitCreateDto) {

    }

    @Override
    public List<CommitResultDto> getResult() {
        return null;
    }

    @Override
    public List<RankInfoDto> getRankList(Integer gameStatus) {

        //筛选出特定比赛进程的队伍
        QueryWrapper<Team> tqw = new QueryWrapper<>();
        tqw.le("game_status", gameStatus);
        List<Team> teams = teamMapper.selectList(tqw);

        //筛选出各队伍对应比赛进程的提交，计算平均值得到最终得分
        List<RankInfoDto> rankingList = new ArrayList<>(teams.size());
        QueryWrapper<Commit> cqw = new QueryWrapper<>();
        for (Team team: teams) {
            Double score = 0.0;
            cqw.clear();
            cqw.eq("team_id", team.getId()).eq("game_status", gameStatus);
            List<Commit> commits = commitMapper.selectList(cqw);
            for (Commit commit: commits) {
                score += commit.getScore();
            }
            score /= commits.size();
            rankingList.add(RankInfoDto.builder().teamId(team.getId()).teamName(team.getName()).finalScore(score).build());
        }
        return rankingList;
    }
}
