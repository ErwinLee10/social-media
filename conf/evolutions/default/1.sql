# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table friend (
  user_id                       bigint not null,
  friend_id                     bigint not null,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  is_deleted                    tinyint(1) default 0 not null,
  constraint pk_friend primary key (user_id,friend_id)
);

create table user (
  id                            bigint auto_increment not null,
  email                         varchar(255) not null,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  is_deleted                    tinyint(1) default 0 not null,
  constraint pk_user primary key (id)
);

alter table friend add constraint fk_friend_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_friend_user_id on friend (user_id);

alter table friend add constraint fk_friend_friend_id foreign key (friend_id) references user (id) on delete restrict on update restrict;
create index ix_friend_friend_id on friend (friend_id);


# --- !Downs

alter table friend drop foreign key fk_friend_user_id;
drop index ix_friend_user_id on friend;

alter table friend drop foreign key fk_friend_friend_id;
drop index ix_friend_friend_id on friend;

drop table if exists friend;

drop table if exists user;

