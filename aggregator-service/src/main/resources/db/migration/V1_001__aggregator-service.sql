create table workspaces
(
    workspace_id       bigserial              not null,
    workspace_name     character varying(256) not null,
    description        character varying      not null,
    owner_id           bigint                 not null,
    created_date       timestamptz            not null,
    created_by         bigint                 not null,
    last_modified_date timestamptz,
    last_modified_by   bigint,
    version            bigint                 not null
) with (autovacuum_enabled = true);

alter table workspaces
    add constraint workspaces_workspace_id_pk primary key (workspace_id);

alter table workspaces
    add constraint workspaces_workspace_name_uq unique (workspace_name);

create index idx_workspace_owner_id on workspaces (owner_id);



create table projects
(
    project_id         bigserial              not null,
    workspace_id       bigint                 not null,
    project_title      character varying(256) not null,
    description        character varying      not null,
    created_date       timestamptz            not null,
    created_by         bigint                 not null,
    last_modified_date timestamptz,
    last_modified_by   bigint,
    version            bigint                 not null
) with (autovacuum_enabled = true);

alter table projects
    add constraint projects_project_id_pk primary key (project_id);

alter table projects
    add constraint project_workspace_id_fk foreign key (workspace_id) references workspaces (workspace_id);

alter table projects
    add constraint project_project_title_uq unique (project_title);

create index idx_project_workspace_id on projects (workspace_id);



create table members
(
    member_id          bigint            not null,
    username           character varying not null,
    email              character varying not null,
    first_name         character varying not null,
    last_name          character varying not null,
    middle_name        character varying,
    member_role_id     bigint            not null,
    member_status_id   bigint            not null,
    created_date       timestamptz       not null,
    created_by         bigint            not null,
    last_modified_date timestamptz,
    last_modified_by   bigint,
    version            bigint            not null
) with (autovacuum_enabled = true);

alter table members
    add constraint members_member_id_pk primary key (member_id);


















