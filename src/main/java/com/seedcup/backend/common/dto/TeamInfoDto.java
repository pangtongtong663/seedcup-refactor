package com.seedcup.backend.common.dto;

import com.seedcup.backend.common.po.Team;
import com.seedcup.backend.common.po.User;

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
