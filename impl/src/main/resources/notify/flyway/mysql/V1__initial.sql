create table not_notify_subject (
	id binary(16) not null,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	primary key (id)
) engine=InnoDB;

create table not_notify_subject_watcher (
	subject_id binary(16) not null,
	user_id varchar(50) not null,
	primary key (subject_id,user_id)
) engine=InnoDB;

create table not_notify_type (
	id varchar(50) not null,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	enabled bit not null,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	markdown_template longtext,
	multiple_send bit not null,
	name varchar(50),
	primary key (id)
) engine=InnoDB;

create table not_notify_type_sender (
	type_id varchar(50) not null,
	sender_id varchar(50) not null,
	senders_order integer not null,
	primary key (type_id,senders_order)
) engine=InnoDB;

alter table not_notify_type_sender
	add constraint UKp2pktb6gsktbqkej0lin40f5q unique (type_id,sender_id);

alter table not_notify_subject_watcher
	add constraint FK15n45aop5ol5nncxqj9ul9ui7 foreign key (subject_id)
	references not_notify_subject (id);

alter table not_notify_type_sender
	add constraint FK6e92jj9rx0h01f34c30y84ej1 foreign key (type_id)
	references not_notify_type (id);
