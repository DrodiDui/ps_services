------------------------------------------------------------------------------------------------------------------------
create table users
(
    user_id bigint not null ,
    email varchar(75) not null,
    username varchar(75) not null,
    password varchar(100) not null ,
    first_name varchar(50) not null ,
    family_name varchar(50) not null,
    user_status_id BIGINT not null ,
    created_date       timestamp    not null,
    created_by         bigint       not null default 0,
    last_modified_date timestamp,
    last_modified_by   bigint,
    action_type        varchar(25)
) with (autovacuum_enabled = true);

alter table users add constraint user_id_pk primary key (user_id);
create sequence users_user_id_seq start 1 owned by users.user_id;

------------------------------------------------------------------------------------------------------------------------
create table user_statuses (
    status_id bigint not null,
    status_name varchar(25) not null ,
    description varchar(150) not null ,
    is_active boolean not null
) with (autovacuum_enabled = true);

alter table user_statuses add constraint status_id_pk primary key (status_id);
create sequence user_statuses_status_id_seq start 1 owned by user_statuses.status_id;
alter table users add constraint users_user_status_id_fk foreign key (user_status_id) references user_statuses(status_id);


------------------------------------------------------------------------------------------------------------------------
create table roles(
    role_id bigint not null ,
    role_code varchar(75) not null
) with (autovacuum_enabled = true);

alter table roles add constraint role_id_pk primary key (role_id);
create sequence roles_role_id_seq start 1 owned by roles.role_id;

------------------------------------------------------------------------------------------------------------------------
create table users_roles(
    user_id bigint not null,
    role_id bigint not null,
    space_id bigint not null
) with (autovacuum_enabled = true);

alter table users_roles add constraint users_roles_pk primary key (user_id, role_id);

alter table users_roles add constraint users_roles_users_fk foreign key (user_id) references users(user_id);
alter table users_roles add constraint users_roles_roles_fk foreign key (role_id) references roles(role_id);

------------------------------------------------------------------------------------------------------------------------
create table authorities(
    authority_id bigint not null ,
    authority_code varchar(75) not null,
    description varchar(75) not null
) with (autovacuum_enabled = true);

alter table authorities add constraint authority_id_pk primary key (authority_id);
create sequence authorities_authority_id_seq start 100000 owned by authorities.authority_id;

------------------------------------------------------------------------------------------------------------------------
create table roles_authorities(
    role_id bigint not null ,
    authority_id bigint not null
) with (autovacuum_enabled = true);

alter table roles_authorities add constraint roles_authorities_pk primary key (role_id, authority_id);

alter table roles_authorities add constraint roles_authorities_roles_fk foreign key (role_id) references roles(role_id);
alter table roles_authorities add constraint roles_authorities_authorities_fk foreign key (authority_id) references authorities(authority_id);