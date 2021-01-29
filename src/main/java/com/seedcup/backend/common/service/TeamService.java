package com.seedcup.backend.common.service;

import com.seedcup.backend.common.dto.TeamInfoDto;
import com.seedcup.backend.common.dto.TeamUpdateDto;
import com.seedcup.backend.common.dto.TeamCreateDto;
import com.seedcup.backend.common.exception.AlreadyInTeamException;
import com.seedcup.backend.common.exception.DuplicateInfoException;
import com.seedcup.backend.common.exception.NoTeamException;
import com.seedcup.backend.common.exception.PermissionDeniedException;

import java.util.List;

public interface TeamService {

    void createTeam(TeamCreateDto teamCreateDto) throws DuplicateInfoException, AlreadyInTeamException;

    void editTeamInfo(TeamUpdateDto editIntroductionDtoDto);

    void addMember(Integer userId);

    void delMember(Integer userId);

    TeamInfoDto getTeamInfo(Integer teamId);
}
