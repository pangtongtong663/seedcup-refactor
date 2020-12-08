DROP TABLE `team`;
DROP TABLE `user`;

CREATE TABLE `team`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `leader_id` int(0) NOT NULL COMMENT '队长id',
  `highest_grade` int(0) NOT NULL COMMENT '队伍最高年级',
  `introduction` varchar(10000) NULL COMMENT '队伍介绍',
  `game_status` tinyint(2) NULL DEFAULT 0 COMMENT '比赛状态',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin;

CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '真实姓名',
  `password_md5` varchar(255) NOT NULL,
  `school` varchar(128) NULL COMMENT '学校',
  `college` varchar(128) NULL COMMENT '院系',
  `class_name` varchar(128) NULL COMMENT '班级',
  `email` varchar(255) NULL,
  `phone_number` varchar(32) NULL,
  `created_time` datetime(0) NULL,
  `team_id` int(0) NOT NULL DEFAULT 0 COMMENT '所属队伍id',
  `is_admin` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否是管理员',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin;

