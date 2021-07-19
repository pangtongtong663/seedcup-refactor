package com.seedcup.backend.global;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seedcup.backend.common.dao.TeamMapper;
import com.seedcup.backend.common.dao.UserMapper;
import com.seedcup.backend.common.po.Team;
import com.seedcup.backend.common.po.User;
import com.seedcup.backend.common.service.TeamService;
import com.seedcup.backend.common.service.UserService;
import com.seedcup.backend.rank.dao.CommitMapper;
import com.seedcup.backend.rank.po.Commit;
import com.seedcup.backend.rank.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class StartApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommitMapper commitMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 创建管理员账号
        userService.generateAdminUser("admin01", "admin01");
        userService.generateAdminUser("admin02", "admin02");
        userService.generateAdminUser("admin03", "admin03");

        //创建测试用户
        userService.generateTestUser("test01", "123456");
        userService.generateTestUser("test02", "123456");
        userService.generateTestUser("test03", "123456");
        userService.generateTestUser("test04", "123456");
        userService.generateTestUser("test05", "123456");

        //获取测试用户实体
        User test01 = userMapper.selectOne(new QueryWrapper<User>().eq("username", "test01"));
        User test02 = userMapper.selectOne(new QueryWrapper<User>().eq("username", "test02"));
        User test03 = userMapper.selectOne(new QueryWrapper<User>().eq("username", "test03"));
        User test04 = userMapper.selectOne(new QueryWrapper<User>().eq("username", "test04"));
        User test05 = userMapper.selectOne(new QueryWrapper<User>().eq("username", "test05"));

        //创建测试队伍1,队员:test01(队长) test02
        teamMapper.insert(Team.builder()
                .name("测试队伍1")
                .leaderId(test01.getId())
                .gameStatus(1)
                .highestGrade(18)
                .introduction("12345677654321")
                .build()
        );
        Team team01 = teamMapper.selectOne(new QueryWrapper<Team>().eq("name", "测试队伍1"));
        test01.setTeamId(team01.getId());
        test02.setTeamId(team01.getId());
        userMapper.updateById(test01);
        userMapper.updateById(test02);

        //创建测试队伍2,队员:test03(队长) test04
        teamMapper.insert(Team.builder()
                .name("测试队伍2")
                .leaderId(test03.getId())
                .gameStatus(1)
                .highestGrade(18)
                .introduction("12345677654321")
                .build()
        );
        Team team02 = teamMapper.selectOne(new QueryWrapper<Team>().eq("name", "测试队伍2"));
        test03.setTeamId(team02.getId());
        test04.setTeamId(team02.getId());
        userMapper.updateById(test03);
        userMapper.updateById(test04);

        //创建测试队伍3,队员:test05(队长)
        teamMapper.insert(Team.builder()
                .name("测试队伍3")
                .leaderId(test05.getId())
                .gameStatus(1)
                .highestGrade(18)
                .introduction("12345677654321")
                .build()
        );
        Team team03 = teamMapper.selectOne(new QueryWrapper<Team>().eq("name", "测试队伍3"));
        test05.setTeamId(team03.getId());
        userMapper.updateById(test05);

        //创建提交记录
        commitMapper.insert(Commit.builder()
                .teamId(team01.getId())
                .gameStatus(1)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(97.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team01.getId())
                .gameStatus(1)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(93.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team02.getId())
                .gameStatus(1)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(85.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team02.getId())
                .gameStatus(1)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(99.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team03.getId())
                .gameStatus(1)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(79.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team01.getId())
                .gameStatus(2)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(97.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team01.getId())
                .gameStatus(2)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(93.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team02.getId())
                .gameStatus(2)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(85.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team02.getId())
                .gameStatus(2)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(99.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team01.getId())
                .gameStatus(3)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(97.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team01.getId())
                .gameStatus(3)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(93.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team02.getId())
                .gameStatus(3)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(85.0)
                .build());
        commitMapper.insert(Commit.builder()
                .teamId(team02.getId())
                .gameStatus(3)
                .filePath("/root/")
                .commitTime(LocalDateTime.now())
                .markTime(LocalDateTime.now())
                .score(99.0)
                .build());

        //更新比赛状态
        team01.setGameStatus(3);
        team02.setGameStatus(3);
        team03.setGameStatus(1);
        teamMapper.updateById(team01);
        teamMapper.updateById(team02);
        teamMapper.updateById(team03);
    }
}
