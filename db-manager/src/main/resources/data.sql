create schema if not exists software_mock;

create table if not exists news
(
    nid            bigint auto_increment,
    title          varchar(255) not null comment '新闻标题',
    content        mediumtext   not null comment '新闻内容',
    type           varchar(128) not null comment '新闻类型',
    published_time datetime     not null comment '新闻发布时间',
    constraint news_pk
        primary key (nid)
);
