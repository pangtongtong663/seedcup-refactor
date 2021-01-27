package com.seedcup.backend.rank.controller;

import com.seedcup.backend.global.dto.ResponseDto;
import com.seedcup.backend.global.dto.SheetDto;
import com.seedcup.backend.global.dto.StandardResponse;
import com.seedcup.backend.rank.dto.RankInfoDto;
import com.seedcup.backend.rank.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rank", produces = "application/json")
public class RankController {

    @Autowired
    private RankService rankService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseDto<Object> getRankingList(@RequestParam Integer gameStatus) {
        return StandardResponse.ok(rankService.getRankList(gameStatus));
    }
}
