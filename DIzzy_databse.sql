CREATE DATABASE IF NOT EXISTS `demo_account`;
USE  `demo_account`;

DROP table if exists `account`;
CREATE TABLE `account`(
`id` int(11) NOT NULL auto_increment,
`name` varchar(255) default NULL,
`password` varchar(255) default NULL,
`email` varchar(255) default NULL,
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