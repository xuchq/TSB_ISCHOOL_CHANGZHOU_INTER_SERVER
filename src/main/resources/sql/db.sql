create table t_s_org_code
(
  id                   char(32) not null comment '主键',
  C_ORGANIZATIONNAME   varchar(50) comment '组织名称',
  C_CODE               varchar(32) comment '组织code',
  is_use               tinyint unsigned default 1 comment '是否可用(0=不可用；1=可用)',
  primary key (id)
);

alter table t_s_org_code comment '机构编码';

create table t_s_user_auth
(
   id                   char(32) not null comment '主键',
   C_USERID             char(32) comment '用户ID',
   C_STATE              tinyint default 1 comment '认证状态 0 未认证 1认证',
   is_use               tinyint unsigned default 1 comment '是否可用(0=不可用；1=可用)',
   primary key (id)
);

alter table t_s_user_auth comment '用户认证状态';