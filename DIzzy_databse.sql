CREATE DATABASE IF NOT EXISTS `demo_account`;
USE  `demo_account`;

DROP table if exists `account`;
CREATE TABLE `account`(
`id` int(11) NOT NULL auto_increment,
`name` varchar(255) default NULL,
`password` varchar(255) default NULL,
`email` varchar(255) default NULL,
`photo` varchar(256) DEFAULT NULL,
`confirm_token` varchar(255) default null,

primary key (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;


DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities`(
`id` int(16) NOT NULL AUTO_INCREMENT,
`authority` varchar(16) NOT NULL,

PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `accounts_and_roles`;
CREATE TABLE `accounts_and_roles`(
`id_name` int(16) NOT NULL,
`id_authority` int(16) NOT NULL,

PRIMARY KEY(`id_name`,`id_authority`),
CONSTRAINT `id_usr_fkey` FOREIGN KEY(`id_name`) REFERENCES `account`(`id`)
 ON DELETE NO ACTION ON UPDATE NO ACTION,

CONSTRAINT `id_ath_fkey` FOREIGN KEY(`id_authority`) REFERENCES `authorities`(`id`)
 ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


INSERT INTO `authorities` (authority) VALUES
('ROLE_MODERATOR'),
('ROLE_USER');

SET FOREIGN_KEY_CHECKS = 1;

Drop table if exists `files`;
CREATE TABLE `files`(
`id` int(11) NOT NULL auto_increment,
`user_name` varchar(256) DEFAULT NULL NULL,
`path`varchar(256) DEFAULT NULL,
`title` varchar(256) default null,
`likes` int (11) Default 0,
`dislikes` int(11) default 0,
`date` datetime DEFAULT NULL,

primary key(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`(
`id` int(11) NOT NULL AUTO_INCREMENT,
`file_id` int(11) default null,
`user_name`  varchar(256) default null,
`comment` varchar(512) default null,
`date` datetime DEFAULT NULL,

primary key(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `reported`;
CREATE TABLE `reported`(
`id` int(11) not null auto_increment,
`file_id` int(11) default null,
`reporting_user` varchar(256) default null,

primary key(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `reported_comments`;
CREATE TABLE `reported_comments`(
`id` int(11) not null auto_increment,
`comment_id` int(11) default null,
`reporting_user` varchar(256) default null,

primary key(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;