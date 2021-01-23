package com.seedcup.backend.common.service;

import com.seedcup.backend.common.dto.TeamInfoDto;
import com.seedcup.backend.common.dto.TeamUpdateDto;
import com.seedcup.backend.common.dto.TeamCreateDto;
import com.seedcup.backend.common.exception.AlreadyInTeamException;
import com.seedcup.backend.common.exception.DuplicateInfoException;
import com.seedcup.backend.common.exception.NoTeamException;
import com.seedcup.backend.common.exception.PermissionDeniedException;

public interface TeamService {

    void createTeam(TeamCreateDto teamCreateDto) throws DuplicateInfoException, AlreadyInTeamException;

    void editTeamInfo(TeamUpdateDto editIntroductionDtoDto) throws NoTeamException, PermissionDeniedException;

    void addMember(Integer userId) throws NoTeamException, PermissionDeniedException;

    void delMember(Integer userId) throws NoTeamException, PermissionDeniedException;

    TeamInfoDto getTeamInfo();

}
