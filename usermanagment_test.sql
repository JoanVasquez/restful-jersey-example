CREATE DATABASE IF NOT EXISTS usermanagment_test;
USE usermanagment_test;
DROP TABLE IF EXISTS `Users`;
CREATE TABLE `users` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `password` blob NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `uk_email` (`email`)
);
