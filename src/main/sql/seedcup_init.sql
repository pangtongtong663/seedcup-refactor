DROP TABLE `team`;
DROP TABLE `user`;

CREATE TABLE `team`  (
  `id` int(0) NOT NULL,
  `name` varchar(255) NOT NULL,
  `leader_id` int(0) NOT NULL COMMENT '队长id',
  `highest_grade` int(0) NOT NULL COMMENT '队伍最高年级',
  `introduction` varchar(10000) NULL COMMENT '队伍介绍',
  `game_status` tinyint(1) NULL DEFAULT 0 COMMENT '比赛状态',
  PRIMARY KEY (`id`)
);

CREATE TABLE `user`  (
  `id` int(0) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password_md5` varchar(255) NOT NULL,
  `phone_number` varchar(32) NULL,
  `school` varchar(128) NOT NULL COMMENT '学校',
  `college` varchar(128) NOT NULL COMMENT '院系',
  `class` varchar(128) NOT NULL COMMENT '班级',
  `email` varchar(255) NULL,
  `created_time` datetime(0) NULL,
  `team_id` int(0) NOT NULL DEFAULT 0 COMMENT '所属队伍id',
  PRIMARY KEY (`id`)
);

