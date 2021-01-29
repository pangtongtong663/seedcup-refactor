/*
 Navicat Premium Data Transfer

 Source Server         : local mysql 8.0
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : seedcup

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 28/01/2021 14:43:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for commit
-- ----------------------------
DROP TABLE IF EXISTS `commit`;
CREATE TABLE `commit`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `score` decimal(14, 2) NOT NULL COMMENT '得分',
  `game_status` tinyint(1) NOT NULL COMMENT '所属比赛进程',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '提交的文件路径',
  `team_id` int(0) NOT NULL COMMENT '所属队伍',
  `mark_time` datetime(0) NULL DEFAULT NULL COMMENT '打分时间',
  `commit_time` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commit
-- ----------------------------
INSERT INTO `commit` VALUES (14, 97.00, 1, '/root/', 18, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (15, 93.00, 1, '/root/', 18, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (16, 85.00, 1, '/root/', 19, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (17, 99.00, 1, '/root/', 19, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (18, 79.00, 1, '/root/', 20, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (19, 97.00, 2, '/root/', 18, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (20, 93.00, 2, '/root/', 18, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (21, 85.00, 2, '/root/', 19, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (22, 99.00, 2, '/root/', 19, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (23, 97.00, 3, '/root/', 18, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (24, 93.00, 3, '/root/', 18, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (25, 85.00, 3, '/root/', 19, '2021-01-28 14:28:30', '2021-01-28 14:28:30');
INSERT INTO `commit` VALUES (26, 99.00, 3, '/root/', 19, '2021-01-28 14:28:30', '2021-01-28 14:28:30');

-- ----------------------------
-- Table structure for team
-- ----------------------------
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

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES (18, '测试队伍1', 68, 18, '12345677654321', 3);
INSERT INTO `team` VALUES (19, '测试队伍2', 70, 18, '12345677654321', 3);
INSERT INTO `team` VALUES (20, '测试队伍3', 72, 18, '12345677654321', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (65, 'admin01', 'bc003e485e4f5d89e33dc2700007bbad', '', '', '', 'admin01@admin.com', '12345678901', '2021-01-28 14:28:30', -1, 1);
INSERT INTO `user` VALUES (66, 'admin02', '45e07a2c7982a01d5ca25768a92e3572', '', '', '', 'admin02@admin.com', '12345678902', '2021-01-28 14:28:30', -1, 1);
INSERT INTO `user` VALUES (67, 'admin03', '7249f1f97c23e5c55b16351396746e90', '', '', '', 'admin03@admin.com', '12345678903', '2021-01-28 14:28:30', -1, 1);
INSERT INTO `user` VALUES (68, 'test01', '5fad3184cd2ba2dda87e473b684325c5', 'test sc', 'test co', 'test class', 'test01@test.com', '12345678971', '2021-01-28 14:28:30', 18, 0);
INSERT INTO `user` VALUES (69, 'test02', '5eafeafdaebab40c7b364b4b6c920279', 'test sc', 'test co', 'test class', 'test02@test.com', '12345678988', '2021-01-28 14:28:30', 18, 0);
INSERT INTO `user` VALUES (70, 'test03', '6fa0a30320a223fa79bc7c335532e92c', 'test sc', 'test co', 'test class', 'test03@test.com', '12345678977', '2021-01-28 14:28:30', 19, 0);
INSERT INTO `user` VALUES (71, 'test04', '93b513efec0485670628c05652fd628e', 'test sc', 'test co', 'test class', 'test04@test.com', '12345678974', '2021-01-28 14:28:30', 19, 0);
INSERT INTO `user` VALUES (72, 'test05', '5c64de2b6dc0272a00c21d28212a3674', 'test sc', 'test co', 'test class', 'test05@test.com', '12345678940', '2021-01-28 14:28:30', 20, 0);

SET FOREIGN_KEY_CHECKS = 1;
