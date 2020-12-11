package com.seedcup.seedcupbackend.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.seedcup.seedcupbackend.common.po.Team;
import com.seedcup.seedcupbackend.common.po.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class TeamInfoDto extends Team {
    private List<User> members;

    public TeamInfoDto (Team team, List<User> members) {
        this.setId(team.getId());
        this.setName(team.getName());
        this.setLeaderId(team.getLeaderId());
        this.setHighestGrade(team.getHighestGrade());
        this.setIntroduction(team.getIntroduction());
        this.setGameStatus(team.getGameStatus());
        this.members = members;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
