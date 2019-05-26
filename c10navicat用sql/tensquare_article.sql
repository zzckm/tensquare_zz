/*
Navicat MySQL Data Transfer

Source Server         : 10c
Source Server Version : 50721
Source Host           : 192.168.253.142:3306
Source Database       : tensquare_article

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-04-15 23:58:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_article`
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `columnid` varchar(20) DEFAULT NULL COMMENT '专栏ID',
  `userid` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '文章正文',
  `image` varchar(100) DEFAULT NULL COMMENT '文章封面',
  `createtime` datetime DEFAULT NULL COMMENT '发表日期',
  `updatetime` datetime DEFAULT NULL COMMENT '修改日期',
  `ispublic` varchar(1) DEFAULT NULL COMMENT '是否公开',
  `istop` varchar(1) DEFAULT NULL COMMENT '是否置顶',
  `visits` int(20) DEFAULT NULL COMMENT '浏览量',
  `thumbup` int(20) DEFAULT NULL COMMENT '点赞数',
  `comment` int(20) DEFAULT NULL COMMENT '评论数',
  `state` varchar(1) DEFAULT NULL COMMENT '审核状态',
  `channelid` varchar(20) DEFAULT NULL COMMENT '所属频道',
  `url` varchar(100) DEFAULT NULL COMMENT 'URL',
  `type` varchar(1) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章';

-- ----------------------------
-- Records of tb_article
-- ----------------------------
INSERT INTO `tb_article` VALUES ('1', null, null, 'aaa', 'bbbb', null, null, '2019-04-08 14:29:32', null, null, null, '3', null, '1', null, null, null);
INSERT INTO `tb_article` VALUES ('2', null, null, 'ccc', 'dddd', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_article` VALUES ('3', null, null, 'eee', 'ffff', null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `tb_channel`
-- ----------------------------
DROP TABLE IF EXISTS `tb_channel`;
CREATE TABLE `tb_channel` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `name` varchar(100) DEFAULT NULL COMMENT '频道名称',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='频道';

-- ----------------------------
-- Records of tb_channel
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_column`
-- ----------------------------
DROP TABLE IF EXISTS `tb_column`;
CREATE TABLE `tb_column` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `name` varchar(100) DEFAULT NULL COMMENT '专栏名称',
  `summary` varchar(1000) DEFAULT NULL COMMENT '专栏简介',
  `userid` varchar(20) DEFAULT NULL COMMENT '用户ID',
  `createtime` datetime DEFAULT NULL COMMENT '申请日期',
  `checktime` datetime DEFAULT NULL COMMENT '审核日期',
  `state` varchar(1) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专栏';

-- ----------------------------
-- Records of tb_column
-- ----------------------------
