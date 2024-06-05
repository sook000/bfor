DROP DATABASE IF EXISTS bforweb;

CREATE DATABASE IF NOT EXISTS bforweb;

USE bforweb;

CREATE TABLE `user` (
	`user_id` VARCHAR(12) NOT NULL,
	`user_password` VARCHAR(1000) NOT NULL,
	`user_name` VARCHAR(50) NOT NULL,
	`user_tel` VARCHAR(20) NOT NULL,
	`user_birth` VARCHAR(20) NULL,
	`user_email_id` VARCHAR(50) NOT NULL,
	`user_email_domain` VARCHAR(50) NOT NULL,
	`regdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`profile_image` VARCHAR(200) NULL,
	`sido_code` INT NOT NULL,
	`gugun_code` INT NOT NULL,
	`token` VARCHAR(1000) NULL DEFAULT NULL,
	PRIMARY KEY (`user_id`)
);

CREATE TABLE `free_board` (
	`fb_no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK AI',
	`user_id` VARCHAR(12) NOT NULL COMMENT 'delete cascade',
	`title` VARCHAR(50) NOT NULL,
	`content` VARCHAR(4000) NOT NULL,
	`regdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`hit` INT NOT NULL DEFAULT 0,
	`category_id` INT NOT NULL COMMENT 'AI',
	PRIMARY KEY (`fb_no`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `free_board_comment` (
	`cmt_no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK AI',
	`fb_no` INT NOT NULL COMMENT 'PK AI',
	`user_id` VARCHAR(12) NOT NULL COMMENT 'delete cascade',
	`cmt_content` VARCHAR(400) NOT NULL,
	`regdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`cmt_no`),
	FOREIGN KEY (`fb_no`) REFERENCES `free_board` (`fb_no`) ON DELETE CASCADE,
	FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `attraction_info` (
	`content_id` INT NOT NULL,
	`content_type_id` INT NULL COMMENT '12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점',
	`title` VARCHAR(100) NULL,
	`addr1` VARCHAR(100) NULL,
	`addr2` VARCHAR(50) NULL,
	`latitude` DECIMAL(20, 17) NULL,
	`longtitude` DECIMAL(20, 17) NULL,
	`zipcode` VARCHAR(50) NULL,
	`telname` VARCHAR(50) NULL,
	`mlevel` VARCHAR(2) NULL,
	`firstimage1` VARCHAR(200) NULL,
	`firstimage2` VARCHAR(200) NULL,
	`sido_code` INT NULL,
	`gugun_code` INT NULL,
	PRIMARY KEY (`content_id`)
);

CREATE TABLE `attraction_detail` (
	`content_id` INT NOT NULL,
	`cat1` VARCHAR(3) NULL COMMENT 'A02',
	`cat2` VARCHAR(5) NULL COMMENT 'A0208',
	`cat3` VARCHAR(9) NULL COMMENT 'A02080300',
	`created_time` VARCHAR(14) NULL COMMENT '20080118180330',
	`modified_time` VARCHAR(14) NULL COMMENT '20210125132029',
	`booktour` VARCHAR(5) NULL COMMENT '교과서속여행지여부 (1=여행지, 0=해당없음)',
	PRIMARY KEY (`content_id`),
	FOREIGN KEY (`content_id`) REFERENCES `attraction_info` (`content_id`)
);

CREATE TABLE `notice_board` (
	`notice_no` INT NOT NULL AUTO_INCREMENT COMMENT 'AI',
	`admin_id` VARCHAR(12) NOT NULL,
	`notice_content` VARCHAR(4000) NOT NULL,
	`regdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`title` VARCHAR(100) NOT NULL,
	PRIMARY KEY (`notice_no`),
	FOREIGN KEY (`admin_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `attraction_description` (
	`content_id` INT NOT NULL,
	`homepage` VARCHAR(100) NULL,
	`overview` VARCHAR(10000) NULL,
	`telname` VARCHAR(45) NULL,
	PRIMARY KEY (`content_id`),
	FOREIGN KEY (`content_id`) REFERENCES `attraction_info` (`content_id`)
);

CREATE TABLE `sido` (
	`sido_code` INT NOT NULL COMMENT 'PK',
	`sido_name` VARCHAR(30) NULL,
	PRIMARY KEY (`sido_code`)
);

CREATE TABLE `gugun` (
	`gugun_code` INT NOT NULL COMMENT 'PK',
	`sido_code` INT NOT NULL COMMENT 'PK',
	`gugun_name` VARCHAR(30) NULL,
	PRIMARY KEY (`gugun_code`, `sido_code`),
	FOREIGN KEY (`sido_code`) REFERENCES `sido` (`sido_code`)
);

CREATE TABLE `travel_route_board` (
	`trb_no` INT NOT NULL AUTO_INCREMENT COMMENT 'PK AI',
	`user_id` VARCHAR(12) NOT NULL COMMENT 'delete cascade',
	`title` VARCHAR(100) NOT NULL,
	`content` VARCHAR(4000) NOT NULL,
	`startdate` VARCHAR(200) NULL,
	`enddate` VARCHAR(200) NULL,
	`favorite_count` INT NOT NULL DEFAULT 0,
	`hit` INT NOT NULL DEFAULT 0,
	`regdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`fixdate` VARCHAR(20) NULL,
	`total_distance` INT NULL DEFAULT 0 COMMENT 'km',
	PRIMARY KEY (`trb_no`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `hash_tag` (
	`hash_tag_code` INT NOT NULL AUTO_INCREMENT COMMENT 'AI',
	`hash_tag_title` VARCHAR(20) NOT NULL,
	`trb_no` INT NOT NULL COMMENT 'PK AI',
	PRIMARY KEY (`hash_tag_code`),
	FOREIGN KEY (`trb_no`) REFERENCES `travel_route_board` (`trb_no`) ON DELETE CASCADE
);

CREATE TABLE `travel_route_order` (
	`trb_no` INT NOT NULL COMMENT 'PK AI',
	`content_id` INT NOT NULL,
	`order` INT NOT NULL COMMENT '0 : 시작 지점',
	`memo` VARCHAR(200) NULL,
	PRIMARY KEY (`trb_no`, `content_id`),
	FOREIGN KEY (`trb_no`) REFERENCES `travel_route_board` (`trb_no`) ON DELETE CASCADE,
	FOREIGN KEY (`content_id`) REFERENCES `attraction_info` (`content_id`)
);

CREATE TABLE `categories` (
	`category_id` INT NOT NULL AUTO_INCREMENT COMMENT 'AI',
	`category_name` VARCHAR(20) NOT NULL COMMENT '카테고리 : 문의, 자유, 후기, 소식 중 1',
	PRIMARY KEY (`category_id`)
);

CREATE TABLE `user_travelinfo_favorite` (
	`user_id` VARCHAR(12) NOT NULL,
	`content_id` INT NOT NULL,
	PRIMARY KEY (`user_id`, `content_id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
	FOREIGN KEY (`content_id`) REFERENCES `attraction_info` (`content_id`)
);

CREATE TABLE `file_info` (
	`idx` INT NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
	`user_id` VARCHAR(12) NOT NULL,
	`save_folder` VARCHAR(45) NULL,
	`original_file` VARCHAR(50) NULL,
	`save_file` VARCHAR(50) NULL,
	PRIMARY KEY (`idx`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);
