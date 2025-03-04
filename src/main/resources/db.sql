CREATE DATABASE IF NOT EXISTS `demo`;
USE `demo`;

CREATE TABLE `t_user` (
	`id` INT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
	`username` VARCHAR(50) NOT NULL COMMENT '用户名' COLLATE 'utf8mb4_0900_ai_ci',
	`password` VARCHAR(128) NOT NULL COMMENT '密码密文' COLLATE 'utf8mb4_0900_ai_ci',
	`role_name` VARCHAR(50) NOT NULL COMMENT '角色名' COLLATE 'utf8mb4_0900_ai_ci',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='用户表'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=6
;

CREATE TABLE `t_article` (
	`id` INT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`title` VARCHAR(50) NOT NULL COMMENT '文章标题' COLLATE 'utf8mb4_0900_ai_ci',
	`content` TEXT NOT NULL COMMENT '文章内容' COLLATE 'utf8mb4_0900_ai_ci',
	`post_date` DATETIME NOT NULL COMMENT '文章发布日期',
	`user_id` INT NOT NULL COMMENT '文章博主ID',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_t_article_t_user` (`user_id`) USING BTREE,
	CONSTRAINT `FK_t_article_t_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COMMENT='文章表'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=6
;
