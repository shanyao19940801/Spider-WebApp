
#创建usertoken表
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for usertoken
-- ----------------------------
DROP TABLE IF EXISTS `usertoken`;
CREATE TABLE `usertoken` (
  `user_token` varchar(100) NOT NULL,
  PRIMARY KEY (`user_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;