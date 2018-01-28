create table article
(
	id int auto_increment
		primary key,
	content varchar(16384) null,
	author int not null,
	like_num int default '0' null,
	abuse_num int default '0' null,
	create_time datetime not null,
	edit_time datetime null,
	valid_time datetime null,
	show_hide_status tinyint(1) default '1' null,
	title varchar(128) not null
)
;

create index article_user_id_fk
	on article (author)
;

create table attachment
(
	id int auto_increment
		primary key,
	filename varchar(128) not null,
	path varchar(512) not null,
	mime varchar(32) not null,
	attach_type varchar(1) not null,
	ownby int not null,
	isActivate tinyint(1) default '0' null,
	constraint attachment_article_id_fk
	foreign key (ownby) references article (id)
)
;

create index attachment_comment_id_fk
	on attachment (ownby)
;

create table comment
(
	id int auto_increment
		primary key,
	content varchar(1024) null,
	commenter int not null,
	create_time datetime not null,
	edit_time datetime null,
	abuse_num int default '0' null,
	show_hide_status tinyint(1) default '1' null,
	parent_article int not null,
	parent_comment int null,
	constraint comment_article_id_fk
	foreign key (parent_article) references article (id),
	constraint comment_comment_id_fk
	foreign key (parent_comment) references comment (id)
)
;

create index comment_article_id_fk
	on comment (parent_article)
;

create index comment_comment_id_fk
	on comment (parent_comment)
;

create index comment_user_id_fk
	on comment (commenter)
;

alter table attachment
	add constraint attachment_comment_id_fk
foreign key (ownby) references comment (id)
;

create table follow_relation
(
	id int auto_increment
		primary key,
	followee int not null,
	follower int not null,
	isValid tinyint(1) default '1' not null
)
;

create index follow_relation_followed_id_fk
	on follow_relation (followee)
;

create index follow_relation_following_id_fk
	on follow_relation (follower)
;

create table user
(
	id int auto_increment
		primary key,
	user_name varchar(128) not null,
	password varchar(512) not null,
	email varchar(128) not null,
	f_name varchar(32) null,
	l_name varchar(32) null,
	gender int null,
	dob date null,
	system_role int not null,
	create_time datetime not null,
	country varchar(32) null,
	description varchar(512) null,
	isValid tinyint(1) default '1' not null,
	constraint user_user_name_uindex
	unique (user_name),
	constraint user_email_uindex
	unique (email)
)
;

alter table article
	add constraint article_user_id_fk
foreign key (author) references user (id)
;

alter table attachment
	add constraint attachment_user_id_fk
foreign key (ownby) references user (id)
;

alter table comment
	add constraint comment_user_id_fk
foreign key (commenter) references user (id)
;

alter table follow_relation
	add constraint follow_relation_followed_id_fk
foreign key (followee) references user (id)
;

alter table follow_relation
	add constraint follow_relation_user_id_fk
foreign key (followee) references user (id)
;

alter table follow_relation
	add constraint follow_relation_following_id_fk
foreign key (follower) references user (id)
;

