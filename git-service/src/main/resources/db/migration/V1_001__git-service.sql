create table repositories
(
    repository_id   bigserial         not null,
    repository_name character varying not null,
    project_id      bigint            not null,
    created_time    timestamptz       not null,
    created_by      bigint            not null,
    modified_time   timestamptz       not null,
    modified_by     bigint            not null,
    owner_id        bigint            not null,
    space_id        bigint            not null
) with (autovacuum_enabled = true);

alter table repositories
    add constraint repositories_repository_id_pk primary key (repository_id);

alter table repositories
    add constraint repositories_project_id_uq unique (project_id);