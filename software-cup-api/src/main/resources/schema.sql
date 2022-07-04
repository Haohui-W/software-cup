create schema software_cup_db;
use software_cup_db;

create table if not exists news
(
    nid             bigint          auto_increment  primary key comment '唯一主键',
    title           varchar(255)    not null                    comment '新闻标题',
    content         mediumtext      not null                    comment '新闻内容',
    type            varchar(128)    not null                    comment '新闻类型',
    published_time  datetime        not null                    comment '新闻发布时间',
    key_words       mediumtext      not null                    comment '新闻关键词组成新闻画像，其内容为json数组 ["关键词1", "关键词2",...]'
    ) comment '实际新闻表，定时更新内容，数据从新闻api中拉取';

create table if not exists user(
    uuid            varchar(255)                    primary key comment '用户在整个系统中的唯一主键，使用java UUID类生成',
    nick_name       varchar(255)    not null                    comment '用户昵称',
    username        varchar(255)    not null        unique      comment '用户登录名',
    user_password   varchar(255)    null                        comment '用户登录密码',
    key_words       mediumtext      not null                    comment '用户关键词，组成用户画像，其内容为json数组 ["关键词1", "关键词2",...]',
    birthday        date            null                        comment '用户生日'
    ) comment '用户表，';

create table if not exists user_behavior(
                                            u_b_id          bigint          auto_increment  primary key comment '用户行为信息的唯一主键',
                                            uuid            varchar(255)    not null                    comment '产生当前行为的用户',
    nid             bigint          not null                    comment '用户看的新闻id',
    duration        bigint          not null                    comment '用户看了这个新闻多少ms'
    ) comment '用户产生的行为表';

create table if not exists user_log(
                                       u_l_id          bigint          auto_increment  primary key,
                                       uuid            varchar(255)    not null                    comment '登录或者登出的用户id',
    login_time      datetime        not null                    comment '用户登录时间',
    duration        bigint          null                        comment '本次登录时间',
    logout_time     datetime        null                        comment '用户登出时间，用户退出程序时记录'
    ) comment '用户登录登出日志';

create table if not exists app_config(
                                         a_c_id          bigint          auto_increment  primary key,
                                         time_pull_news  bigint          not null                    comment '拉取新闻的时间'
) comment '程序配置表，程序启动时读取并配置';