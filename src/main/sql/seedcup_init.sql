SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `commit`;
CREATE TABLE `commit`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `score` decimal(14, 2) NOT NULL COMMENT '得分',
  `game_status` tinyint(1) NOT NULL COMMENT '所属比赛进程',
  `file_path` varchar(255) NOT NULL COMMENT '提交的文件路径',
  `team_id` int(0) NOT NULL COMMENT '所属队伍',
  `mark_time` datetime(0) NULL COMMENT '打分时间',
  `commit_time` datetime(0) NULL COMMENT '提交时间',
  PRIMARY KEY (`id`)
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `leader_id` int(0) NOT NULL COMMENT '队长id',
  `highest_grade` int(0) NOT NULL COMMENT '队伍最高年级',
  `introduction` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '队伍介绍',
  `game_status` tinyint(1) NULL DEFAULT 0 COMMENT '比赛状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '真实姓名',
  `password_md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `school` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学校',
  `college` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '院系',
  `class_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '班级',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `phone_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `created_time` datetime(0) NULL DEFAULT NULL,
  `team_id` int(0) NOT NULL DEFAULT 0 COMMENT '所属队伍id',
  `is_admin` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否是管理员',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

