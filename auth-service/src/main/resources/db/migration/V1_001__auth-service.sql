create table users
(
    user_id             bigserial         not null,
    username            character varying not null,
    email               character varying not null,
    password            character varying not null,
    first_name          character varying not null,
    last_name           character varying not null,
    is_active           boolean           not null,
    activation_code     character varying,
    created_date        timestamptz       not null,
    last_modifying_date timestamptz,
    created_by          bigint,
    last_modifying_by   bigint
) with (autovacuum_enabled = true);

alter table users
    add constraint users_user_id_pk primary key (user_id);

alter table users
    add constraint users_email_uq unique (email);
alter table users
    add constraint users_username_uq unique (username);


create table roles
(
    role_id             bigserial         not null,
    role_code           character varying not null,
    role_name           character varying not null,
    description         jsonb             not null,
    created_date        timestamptz       not null,
    last_modifying_date timestamptz,
    created_by          bigint,
    last_modifying_by   bigint
) with (autovacuum_enabled = true);


alter table roles
    add constraint roles_role_id_pk primary key (role_id);

alter table roles
    add constraint roles_role_code_uq unique (role_code);
alter table roles
    add constraint roles_role_name_uq unique (role_name);


create table users_role
(
    user_id      bigint      not null,
    role_id      bigint      not null,
    workspace_id bigint      not null,
    created_date timestamptz not null
) with (autovacuum_enabled = true);

alter table users_role
    add constraint users_role_user_role_id_pk primary key (user_id, role_id);

alter table users_role
    add constraint users_role_user_id_fk foreign key (user_id) references users (user_id);
alter table users_role
    add constraint users_role_role_id_fk foreign key (role_id) references roles (role_id);


create table authorities
(
    authority_id   bigserial         not null,
    authority_code character varying not null,
    authority_name character varying not null,
    description    jsonb             not null
) with (autovacuum_enabled = true);

alter table authorities
    add constraint authorities_authority_id_pk primary key (authority_id);
alter table authorities
    add constraint authorities_authority_name unique (authority_name);

create table roles_authority
(
    role_id      bigint not null,
    authority_id bigint not null
) with (autovacuum_enabled = true);

alter table roles_authority
    add constraint roles_authority_role_authority_id primary key (role_id, authority_id);

alter table roles_authority
    add constraint roles_authority_role_id_fk foreign key (role_id) references roles (role_id);
alter table roles_authority
    add constraint roles_authority_authority_id_fk foreign key (authority_id) references authorities (authority_id);









