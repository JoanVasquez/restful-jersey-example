CREATE DATABASE IF NOT EXISTS usermanagment;
USE usermanagment;
DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `password` blob NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `uc_email` (`email`)
);
