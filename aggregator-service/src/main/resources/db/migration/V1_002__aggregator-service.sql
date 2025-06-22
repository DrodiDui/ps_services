alter table projects add column project_type_id bigint not null;

create table workspace_members (
    workspace_id bigint not null ,
    member_id bigint not null ,
    role_id bigint not null ,
    created_date timestamptz not null
) with (autovacuum_enabled = true);