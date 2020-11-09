# 示例数据库准备

```mysql
CREATE TABLE `country` (
	`id` int NOT NULL AUTO_INCREMENT,
	`countryname` varchar(255) NULL,
	`countrycode` varchar(255) NULL,
	PRIMARY KEY (`id`)
);

insert country(`countryname`, `countrycode`)
values ('中国', 'CN'), ('美国', 'US'), ('俄罗斯', 'RU'), ('英国', 'GB'), ('法国', 'FR');
```



```mysql
create table sys_user
(
id bigint not null auto_increment comment '用户ID',
user_name varchar(50) comment '用户名',
user_password varchar(50) comment '密码',
user_email varchar(50) comment '邮箱',
user_info text comment '简介',
head_img blob comment '头像',
create_time datetime comment '创建时间',
primary key (id)
);

alter table  sys_user comment '用户表';
```




```mysql
create table sys_role
(
id bigint not null auto_increment comment '角色ID',
role_name varchar(50) comment '角色名',
enabled int comment '有效标志',
create_by bigint comment '创建人',
create_time datetime comment '创建时间',
primary key (id)
);

alter table sys_role comment '角色表';
```


```mysql
create table sys_privilege
(
  id bigint not null auto_increment comment '权限ID',
  privilege_name varchar(50) comment '权限名称',
  privilege_url varchar(200) comment '权限URL',
  primary key (id)
);

alter table sys_privilege comment '权限表';

create table sys_user_role
(
  user_id bigint comment '用户ID',
  role_id bigint comment '角色ID'
);

alter table sys_user_role comment '用户角色关联表';

create table sys_role_privilege
(
  role_id bigint comment '角色ID',
  privilege_id bigint comment '权限ID'
);

alter table sys_role_privilege comment '角色权限关联表';
```

```mysql
INSERT INTO `sys_user` VALUES ('1', 'admin', '123456', 'admin@mybatis.tk', '管理员', null, '2016-04-01 17:00:58');

INSERT INTO `sys_user` VALUES ('1001', 'test', '123456', 'test@mybatis.tk', '测试用户', null, '2016-04-01 17:01:52');

INSERT INTO `sys_role` VALUES ('1', '管理员', '1', '1', '2016-04-01 17:02:14');

INSERT INTO `sys_role` VALUES ('2', '普通用户', '1', '1', '2016-04-01 17:02:34');

INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('1', '2');
INSERT INTO `sys_user_role` VALUES ('1001', '2');

INSERT INTO `sys_privilege` VALUES ('1', '用户管理', '/users');
INSERT INTO `sys_privilege` VALUES ('2', '角色管理', '/roles');
INSERT INTO `sys_privilege` VALUES ('3', '系统日志', '/logs');
INSERT INTO `sys_privilege` VALUES ('4', '人员维护', '/persons');
INSERT INTO `sys_privilege` VALUES ('5', '单位维护', '/companies');

INSERT INTO `sys_role_privilege` VALUES ('1', '1');
INSERT INTO `sys_role_privilege` VALUES ('1', '2');
INSERT INTO `sys_role_privilege` VALUES ('1', '3');
INSERT INTO `sys_role_privilege` VALUES ('2', '4');
INSERT INTO `sys_role_privilege` VALUES ('2', '5');
```

```mysql
ALTER TABLE `sys_user`
MODIFY COLUMN `user_email` varchar(50) NULL DEFAULT
'test@mybatis.tk' COMMENT '邮箱' AFTER `user_password`;
```

第一个存储过程

```SQL
# 第一个存储过程
# 根据用户id查询用户其他信息
# 方法看起来很奇怪，但是展示了多个输出参数

DROP PROCEDURE IF EXISTS `select_user_by_id`;
DELIMITER ;;
CREATE PROCEDURE `select_user_by_id`(
    IN userId BIGINT,
    OUT userName VARCHAR(50),
    OUT userPassword VARCHAR(50),
    OUT userEmail VARCHAR(50),
    OUT userInfo TEXT,
    OUT headImg BLOB,
    OUT createTime DATETIME
)
BEGIN
# 根据用户id查询其他数据
select
user_name, user_password, user_email, user_info, head_img, create_time
INTO
userName, userPassword, userEmail, userInfo, headImg, createTime
from sys_user
WHERE id = userId;
END
;;
DELIMITER ;
```



第二个存储过程

```sql
# 第二个存储过程
# 简单根据用户名和分页参数进行查询，返回总数和分页数据
DROP PROCEDURE IF EXISTS `select_user_page`;
DELIMITER ;;
CREATE PROCEDURE `select_user_page`(
    IN userName VARCHAR(50),
    IN _offset BIGINT,
    IN _limit BIGINT,
    OUT total BIGINT
)
BEGIN
# 查询数据总数
select count(*) INTO total
from sys_user
where user_name like concat('%', userName, '%');
# 分页查询数据
select *
from sys_user
where user_name like concat('%', userName, '%')
limit _offset, _limit;
END
;;
DELIMITER ;
```

第三个存储过程

```sql
# 第三个存储过程
# 保存用户信息和用户关联信息
DROP PROCEDURE IF EXISTS `insert_user_and_roles`;
DELIMITER ;;
CREATE PROCEDURE `insert_user_and_role`(
    OUT userId BIGINT,
    IN userName VARCHAR(50),
    IN userPassword VARCHAR(50),
    IN userEmail VARCHAR(50),
    IN userInfo TEXT,
    IN headImg BLOB,
    OUT createTime DATETIME,
    IN roleIds VARCHAR(200)
)
BEGIN
# 设置当前时间
SET createTime = NOW();
# 插入数据
INSERT INTO sys_user(user_name, user_password, user_email, user_info, head_img, create_time)
VALUES (userName, userPassword, userEmail, userInfo, headImg, createTime);
# 获取自增主键
SELECT LAST_INSERT_ID() INTO userId;
# 保存用户和角色关系数据
SET roleIds = CONCAT(',', roleIds, ',');
INSERT INTO sys_user_role(user_id, role_id)
select userId, id from sys_role
where INSTR(roleIds, CONCAT(',', id, ',')) > 0;
END
;;
DELIMITER ;
```

第四个存储过程

```SQL
# 第四个存储过程
# 删除用户信息和角色关联信息
DROP PROCEDURE IF EXISTS `delete_user_by_id`;
DELIMITER ;;
CREATE PROCEDURE `delete_user_by_id`(IN userId BIGINT)
BEGIN
DELETE FROM sys_user_role where user_id=userId;
DELETE FROM sys_user where id=userId;
END
;;
DELIMITER ;
```





