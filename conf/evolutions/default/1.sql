# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table friend_connection (
  user_id                       bigint not null,
  friend_id                     bigint not null,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  is_deleted                    tinyint(1) default 0 not null,
  constraint pk_friend_connection primary key (user_id,friend_id)
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

create table user_update_subscription (
  requestor_id                  bigint not null,
  target_id                     bigint not null,
  subscription_status           integer,
  version                       bigint not null,
  when_created                  datetime(6) not null,
  when_modified                 datetime(6) not null,
  is_deleted                    tinyint(1) default 0 not null,
  constraint ck_user_update_subscription_subscription_status check (subscription_status in ('2','1')),
  constraint pk_user_update_subscription primary key (requestor_id,target_id)
);

alter table friend_connection add constraint fk_friend_connection_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_friend_connection_user_id on friend_connection (user_id);

alter table friend_connection add constraint fk_friend_connection_friend_id foreign key (friend_id) references user (id) on delete restrict on update restrict;
create index ix_friend_connection_friend_id on friend_connection (friend_id);

alter table user_update_subscription add constraint fk_user_update_subscription_requestor_id foreign key (requestor_id) references user (id) on delete restrict on update restrict;
create index ix_user_update_subscription_requestor_id on user_update_subscription (requestor_id);

alter table user_update_subscription add constraint fk_user_update_subscription_target_id foreign key (target_id) references user (id) on delete restrict on update restrict;
create index ix_user_update_subscription_target_id on user_update_subscription (target_id);


# --- !Downs

alter table friend_connection drop foreign key fk_friend_connection_user_id;
drop index ix_friend_connection_user_id on friend_connection;

alter table friend_connection drop foreign key fk_friend_connection_friend_id;
drop index ix_friend_connection_friend_id on friend_connection;

alter table user_update_subscription drop foreign key fk_user_update_subscription_requestor_id;
drop index ix_user_update_subscription_requestor_id on user_update_subscription;

alter table user_update_subscription drop foreign key fk_user_update_subscription_target_id;
drop index ix_user_update_subscription_target_id on user_update_subscription;

drop table if exists friend_connection;

drop table if exists user;

drop table if exists user_update_subscription;

