create table chapter(

 id int(11) unsigned not null AUTO_INCREMENT,
 video_id int(11) DEFAULT NULL COMMENT '视频主键',
 title varchar(128) DEFAULT NULL COMMENT '章节名称',
 ordered int(11) DEFAULT  NULL COMMENT '章节顺序',
 create_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
 PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT  CHARSET=utf8;



DROP TABLE  IF EXISTS  comment;

create table comment(
 id int(11) unsigned NOT  NULL AUTO_INCREMENT,
 content varchar(256) DEFAULT NULL COMMENT '内容',
 user_id int(11) DEFAULT null,
 head_img varchar(128)  DEFAULT NULL COMMENT '用户头像',
 name varchar(128)  DEFAULT NULL COMMENT '昵称',
 point double(5,2) DEFAULT NULL  COMMENT '评分，10分满分',
 up int(11)  DEFAULT NULL COMMENT '点赞数',
 create time timestamp NULL  DEFAULT NULL COMMENT '创建时间',
 order_id int(11) DEFAULT NULL COMMENT '订单id',
 video_id int(11) DEFAULT NULL  COMMENT '视频id',
 PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT  CHARSET=utf8;


DROP TABLE  IF EXISTS  episode;

create table episode(
  id int(11)  unsigned NOT NULL AUTO_INCREMENT,
  title varchar(524) DEFAULT NULL COMMENT '集标题',
  num  int(10) DEFAULT NULL COMMENT  ' 第几集',
  duration  varchar(64)  DEFAULT NULL COMMENT '时长 分钟  单位',
  cover_img varchar(524)  DEFAULT NULL  COMMENT '封面页',
  video_id int(10)  DEFAULT NULL COMMENT '视频id',
  summary varchar(256)  DEFAULT NULL COMMENT '集概述',
  create_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  chapter_id int(11)  DEFAULT NULL COMMENT '章节主键id',
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT  CHARSET=utf8;

DROP TABLE  IF EXISTS  user;

create table user(
 id int(11)  unsigned NOT NULL AUTO_INCREMENT,
 open_id varchar(128)  DEFAULT NULL COMMENT '微信openid',
 name varchar(128)  DEFAULT NULL COMMENT '昵称',
 head_img varchar(524) DEFAULT NULL COMMENT '头像',
 phone varchar(64) DEFAULT NULL COMMENT '手机号',
 sign varchar(524)  DEFAULT '全栈工程师' COMMENT '用户签名',
 sex tinyint(2) DEFAULT '-1' COMMENT '0表示女,1表示男',
 city varchar(64) DEFAULT NULL COMMENT '城市',
 create_time datetime DEFAULT NULL COMMENT '创建时间',
 PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT  CHARSET=utf8;

DROP TABLE  IF EXISTS  video;

create table video(
id int(11) unsigned NOT NULL AUTO_INCREMENT,
title varchar(524) DEFAULT NULL COMMENT '视频标题',
summary varchar(1026) DEFAULT NULL COMMENT '概述',
cover_img varchar(524) DEFAULT NULL COMMENT '封面图',
view_num int(10) DEFAULT '0' COMMENT '观看数',
price int(11) DEFAULT NULL COMMENT '价格,分',
create_time datetime DEFAULT NULL COMMENT '创建时间',
online int(5) DEFAULT  '0'  COMMENT '0表示未上线，表示上线',
point double(11,2) DEFAULT '8.70' COMMENT '默认8.7，最高10分',
primary key (id)
) ENGINE=InnoDB DEFAULT  CHARSET=utf8;




create table video_order(
id int(11)  unsigned NOT NULL AUTO_INCREMENT,
openid varchar(32) DEFAULT NULL COMMENT '用户表示',
out_trade_no varchar(64) DEFAULT NULL COMMENT '订单唯一标识',
state int(11)  DEFAULT NULL COMMENT '0表示未支付，1表示已支付',
create_time datatime DEFAULT NULL COMMENT '订单生成时间',
notify_time datetime DEFAULT NULL COMMENT '支付回调时间',
total_fee int(11)  DEFAULT NULL COMMENT '支付金额，单位分',
nickname varchar(32) DEFAULT NULL COMMENT '微信昵称',
head_img varchar(128) DEFAULT NULL COMMENT '微信头像',
video_id int(11) DEFAULT NULL COMMENT '视频主键',
video_title varchar(128) DEFAULT NULL COMMENT '视频名称',
video_img varchar(256) DEFAULT NULL COMMENT '视频图片',
user_id int(11)  DEFAULT NULL COMMENT '用户id',
ip varchar(64) DEFAULT NULL COMMENT '用户ip地址',
del int(5) DEFAULT '0' COMMENT '0表示未删除，1表示删除',
PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT  CHARSET=utf8;

create table student(
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(20)  NOT NULL,
  sex varchar(4) NOT NULL,
  birth year(4) NOT NULL,
  department varchar(20) NOT NULL,
  address varchar(50) NOT NULL,
  primary key (id)
)ENGINE=InnoDB DEFAULT  CHARSET=utf8;


INSERT INTO student(name,sex,birth,department,address) values('张老二','男','1986','中文系','北京市昌平区');
INSERT INTO student(name,sex,birth,department,address) values('张三','女','1983','中文系','湖南省永州市');
INSERT INTO student(name,sex,birth,department,address) values('李四','男','1985','英语系','辽宁省阜新市');
INSERT INTO student(name,sex,birth,department,address) values('王五','女','1982','英语系','福建省厦门市');
INSERT INTO student(name,sex,birth,department,address) values('王六','男','1985','计算机系','湖南省衡阳市');

