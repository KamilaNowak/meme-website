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
`enabled` TINYINT NOT NULL,
`mute` datetime DEFAULT NULL,
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


INSERT INTO `account` (name, password, email, photo,confirm_token, enabled, mute) VALUES
("admin","$2a$10$VWCYTN8oYPrw7riyRsyVz.PC0mkhJ3NwS8jcwF3ds0onBF7qd9d2.","admin@gmail.com","https://www.logolynx.com/images/logolynx/23/23938578fb8d88c02bc59906d12230f3.png","",1,'2015-11-05 14:29:36');

INSERT INTO `accounts_and_roles` (id_name, id_authority) VALUES
(1,1);

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

DROP TABLE IF EXISTS `user_file_likes`;
CREATE TABLE `user_file_likes`(
`id` int(16) NOT NULL AUTO_INCREMENT,
`id_user` int(16) DEFAULT NULL,
`id_file` int(16) DEFAULT NULL,

PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_file_dislikes`;
CREATE TABLE `user_file_dislikes`(
`id` int(16) NOT NULL AUTO_INCREMENT,
`id_user` int(16) DEFAULT NULL,
`id_file` int(16) DEFAULT NULL,

PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`(
`id` int(11) NOT NULL AUTO_INCREMENT,
`file_id` int(11) default null,
`user_name`  varchar(256) default null,
`comment` varchar(10000) default null,
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

DROP TABLE IF EXISTS `cubby`;
CREATE TABLE `cubby`(
`id` int(11) not null auto_increment,
`email` varchar(256) DEFAULT NULL,
`file_id` int(11) DEFAULT NULL,

PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;