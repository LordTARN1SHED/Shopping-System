/*
 Navicat Premium Dump SQL

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80012 (8.0.12)
 Source Host           : localhost:3306
 Source Schema         : weborder

 Target Server Type    : MySQL
 Target Server Version : 80012 (8.0.12)
 File Encoding         : 65001

 Date: 18/09/2024 15:12:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `number` int(11) NULL DEFAULT NULL,
  `price` double(10, 2) NULL DEFAULT NULL,
  `pictureurl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES (1, '三体', '刘慈欣', 98, 39.99, '0');
INSERT INTO `books` VALUES (2, '地球大炮', '刘慈欣', 92, 19.90, '0');
INSERT INTO `books` VALUES (3, '乖，摸摸头', '大冰', 97, 9.90, '0');
INSERT INTO `books` VALUES (6, '很贵的书', 'jrd', 1000, 999.00, '0');
INSERT INTO `books` VALUES (7, '很便宜的书', 'jder', 1229, 1.00, '0');
INSERT INTO `books` VALUES (10, 'test', 'jrd', 100, 1000.00, '0');
INSERT INTO `books` VALUES (12, '123', '321', 0, 123.00, '0');
INSERT INTO `books` VALUES (20, '1', '1', 79, 1.00, '0');
INSERT INTO `books` VALUES (21, '2', '2', 197, 2.00, '0');
INSERT INTO `books` VALUES (22, 'syh', 'syh', 11, 1.00, '150_200jpg78cbab91-d54a-4553-b220-65c3503661f8');

-- ----------------------------
-- Table structure for invitations
-- ----------------------------
DROP TABLE IF EXISTS `invitations`;
CREATE TABLE `invitations`  (
  `invitation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` int(4) NOT NULL,
  PRIMARY KEY (`invitation`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of invitations
-- ----------------------------
INSERT INTO `invitations` VALUES ('root', 0);
INSERT INTO `invitations` VALUES ('user', 1);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL,
  `menuCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `menuName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名字',
  `menuLevel` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单级别',
  `menuParentCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单的父code',
  `menuClick` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点击触发的函数',
  `menuRight` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限 0超级管理员，1表示管理员，2表示普通用户，可以用逗号组合使用',
  `menuComponent` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menuIcon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '001', '图书管理', '1', '', 'BooksManage', '0', 'books/BooksManage', 'el-icon-setting');
INSERT INTO `menu` VALUES (2, '002', '商城', '1', NULL, 'Mall', '1', 'Mall', 'el-icon-goods');
INSERT INTO `menu` VALUES (3, '003', '购物车', '1', NULL, 'ShoppingCart', '1', 'ShoppingCart', 'el-icon-shopping-cart-full');
INSERT INTO `menu` VALUES (4, '004', '订单', '1', NULL, 'Order', '1', 'Order', 'el-icon-time');

-- ----------------------------
-- Table structure for orderbook
-- ----------------------------
DROP TABLE IF EXISTS `orderbook`;
CREATE TABLE `orderbook`  (
  `orderid` int(20) NOT NULL,
  `bookid` int(11) NOT NULL,
  `num` int(11) NOT NULL
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of orderbook
-- ----------------------------
INSERT INTO `orderbook` VALUES (26, 3, 2);
INSERT INTO `orderbook` VALUES (25, 2, 1);
INSERT INTO `orderbook` VALUES (25, 1, 1);
INSERT INTO `orderbook` VALUES (24, 21, 1);
INSERT INTO `orderbook` VALUES (24, 20, 1);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `orderid` int(20) NOT NULL AUTO_INCREMENT,
  `payornot` int(1) UNSIGNED ZEROFILL NOT NULL COMMENT '0是取消支付，1是待支付，2是已支付',
  `userid` int(11) NOT NULL,
  `time` datetime(6) NOT NULL ON UPDATE CURRENT_TIMESTAMP(6),
  `totalval` double(11, 2) NOT NULL,
  PRIMARY KEY (`orderid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (24, 2, 70, '2024-09-17 14:56:39.763030', 3.00);
INSERT INTO `orders` VALUES (25, 1, 70, '2024-09-17 14:55:29.050233', 59.00);
INSERT INTO `orders` VALUES (26, 0, 70, '2024-09-17 15:15:14.687938', 19.00);

-- ----------------------------
-- Table structure for review
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review`  (
  `orderid` int(11) NOT NULL,
  `bookid` int(11) NOT NULL,
  `mark` int(1) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`orderid`, `bookid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of review
-- ----------------------------

-- ----------------------------
-- Table structure for shoppingcart
-- ----------------------------
DROP TABLE IF EXISTS `shoppingcart`;
CREATE TABLE `shoppingcart`  (
  `number` int(11) NOT NULL,
  `userid` int(11) NULL DEFAULT NULL,
  `bookid` int(11) NULL DEFAULT NULL,
  INDEX `bookid`(`number` ASC) USING BTREE,
  INDEX `userid`(`userid` ASC) USING BTREE,
  INDEX `bookid_2`(`bookid` ASC) USING BTREE,
  CONSTRAINT `shoppingcart_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `xuser` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `shoppingcart_ibfk_2` FOREIGN KEY (`bookid`) REFERENCES `books` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shoppingcart
-- ----------------------------
INSERT INTO `shoppingcart` VALUES (2, 1, 1);
INSERT INTO `shoppingcart` VALUES (1, 1, 2);
INSERT INTO `shoppingcart` VALUES (2, 64, 1);
INSERT INTO `shoppingcart` VALUES (5, 54, 1);
INSERT INTO `shoppingcart` VALUES (5, 54, 3);
INSERT INTO `shoppingcart` VALUES (2, 54, 2);
INSERT INTO `shoppingcart` VALUES (1, 54, 6);
INSERT INTO `shoppingcart` VALUES (1, 54, 7);
INSERT INTO `shoppingcart` VALUES (1, 64, 2);
INSERT INTO `shoppingcart` VALUES (1, 64, 3);
INSERT INTO `shoppingcart` VALUES (1, 70, 1);
INSERT INTO `shoppingcart` VALUES (1, 70, 2);

-- ----------------------------
-- Table structure for xuser
-- ----------------------------
DROP TABLE IF EXISTS `xuser`;
CREATE TABLE `xuser`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` int(11) NULL DEFAULT NULL,
  `balance` double(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `name`) USING BTREE,
  INDEX `id`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xuser
-- ----------------------------
INSERT INTO `xuser` VALUES (1, 'root', 'root', 0, 100000.00);
INSERT INTO `xuser` VALUES (50, 'user0', '123', 1, 1000.00);
INSERT INTO `xuser` VALUES (54, 'user1', 'user', 1, 1000.00);
INSERT INTO `xuser` VALUES (55, 'user2', 'user', 0, 9999.00);
INSERT INTO `xuser` VALUES (56, 'user3', 'user', 0, 9999.00);
INSERT INTO `xuser` VALUES (57, 'user4', 'user', 1, 1000.00);
INSERT INTO `xuser` VALUES (58, 'user5', 'user', 0, 9999.00);
INSERT INTO `xuser` VALUES (60, 'user6', 'user', 1, 828.87);
INSERT INTO `xuser` VALUES (64, 'userjrd', 'user', 1, 1000.00);
INSERT INTO `xuser` VALUES (65, 'user1234', 'user', 1, 899.30);
INSERT INTO `xuser` VALUES (66, 'root123', 'root', 0, 9999.00);
INSERT INTO `xuser` VALUES (67, 'root1234', 'root', 0, 9999.00);
INSERT INTO `xuser` VALUES (68, 'userjrd', 'user', 1, 1000.00);
INSERT INTO `xuser` VALUES (69, 'rootjrd', 'root', 0, 9999.00);
INSERT INTO `xuser` VALUES (70, 'jrduser', 'jrduser', 1, 335.00);

SET FOREIGN_KEY_CHECKS = 1;
