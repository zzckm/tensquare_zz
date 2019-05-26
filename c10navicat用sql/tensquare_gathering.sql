/*
Navicat MySQL Data Transfer

Source Server         : 10c
Source Server Version : 50721
Source Host           : 192.168.253.142:3306
Source Database       : tensquare_gathering

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-04-15 23:58:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_gathering`
-- ----------------------------
DROP TABLE IF EXISTS `tb_gathering`;
CREATE TABLE `tb_gathering` (
  `id` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '活动名称',
  `summary` text COMMENT '大会简介',
  `detail` text COMMENT '详细说明',
  `sponsor` varchar(100) DEFAULT NULL COMMENT '主办方',
  `image` varchar(100) DEFAULT NULL COMMENT '活动图片',
  `starttime` datetime DEFAULT NULL COMMENT '开始时间',
  `endtime` datetime DEFAULT NULL COMMENT '截止时间',
  `address` varchar(100) DEFAULT NULL COMMENT '举办地点',
  `enrolltime` datetime DEFAULT NULL COMMENT '报名截止',
  `state` varchar(1) DEFAULT NULL COMMENT '是否可见',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动';

-- ----------------------------
-- Records of tb_gathering
-- ----------------------------
INSERT INTO `tb_gathering` VALUES ('1', '测试活动', '喝茶看电影，不亦乐乎', '喝茶看电影，不亦乐乎', '智游工程师', null, '2017-12-14 15:39:32', '2017-12-21 15:39:36', null, null, '1', '1');
INSERT INTO `tb_gathering` VALUES ('94377594140', 'aaaa', null, null, null, null, null, null, null, null, '1', '1');
INSERT INTO `tb_gathering` VALUES ('943776146707845', 'aaaa', null, null, 'ssss', null, null, null, 'cccc', null, '1', '1');
INSERT INTO `tb_gathering` VALUES ('943776663576121344', 'aaaa', null, null, null, null, null, null, null, null, '1', '2');
INSERT INTO `tb_gathering` VALUES ('943783521749700608', '2342423', null, null, '23454534', null, null, null, '545435435', null, '1', '2');
INSERT INTO `tb_gathering` VALUES ('944085821768732672', 'JAVAEE茶话会', null, null, '智游', null, null, null, '金燕龙', null, '1', '2');
INSERT INTO `tb_gathering` VALUES ('944086086991351808', '是', null, null, '11', null, null, null, '11', null, '1', '3');
INSERT INTO `tb_gathering` VALUES ('944090372710207488', '大讨论', null, null, '小马', null, null, null, '消息', null, '1', '3');
INSERT INTO `tb_gathering` VALUES ('944105652622594048', '测试测试', null, null, '测试者', null, null, null, '测试地址', null, '1', '4');
INSERT INTO `tb_gathering` VALUES ('945227340642914304', '111', null, null, '222', null, null, null, '333', null, '1', '5');
INSERT INTO `tb_gathering` VALUES ('945227678527655936', '111', null, null, '222', null, null, null, '333', null, '1', '5');
INSERT INTO `tb_gathering` VALUES ('945235087564345344', '啊啊', null, null, '1', null, null, null, '1', null, '1', '1');
INSERT INTO `tb_gathering` VALUES ('945235534329024512', '1', null, null, '1', null, null, null, '1', null, '1', '2');
INSERT INTO `tb_gathering` VALUES ('945235859786043392', '1', null, null, '1', null, null, null, '1', null, '1', '3');
INSERT INTO `tb_gathering` VALUES ('951384896167874560', '??', null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `tb_usergath`
-- ----------------------------
DROP TABLE IF EXISTS `tb_usergath`;
CREATE TABLE `tb_usergath` (
  `userid` varchar(20) NOT NULL COMMENT '用户ID',
  `gathid` varchar(20) NOT NULL COMMENT '活动ID',
  `exetime` datetime DEFAULT NULL COMMENT '点击时间',
  PRIMARY KEY (`userid`,`gathid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关注活动';

-- ----------------------------
-- Records of tb_usergath
-- ----------------------------
INSERT INTO `tb_usergath` VALUES ('1', '200', '2018-01-06 15:44:04');
