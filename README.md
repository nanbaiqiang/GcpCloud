# springboot-security
### SpringBoot集成Spring security，自定义userdetailservice，从MySQL数据库读取用户信息。
### demo中一共用到两张表，运行项目时会自动创建。
CREATE TABLE `s_role` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) NULL DEFAULT NULL,
	`uid` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `FKpkoo0xfyi6rd0hs9ybqv92fjp` (`uid`),
	CONSTRAINT `FKpkoo0xfyi6rd0hs9ybqv92fjp` FOREIGN KEY (`uid`) REFERENCES `s_user` (`id`)
);

CREATE TABLE `s_user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`dob` DATE NULL DEFAULT NULL,
	`email` VARCHAR(20) NULL DEFAULT NULL,
	`name` VARCHAR(20) NULL DEFAULT NULL,
	`password` VARCHAR(200) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
);

### demo页面使用thymeleaf模板引擎
### demo使用SpringDataJpa实现数据库访问
