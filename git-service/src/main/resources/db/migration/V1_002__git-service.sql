create table branches
(
    branch_id      bigserial         not null,
    branch_name    character varying not null,
    repository_id  bigint            not null,
    branch_version bigint            not null default 1,
    created_time   timestamptz       not null,
    created_by     bigint            not null,
    modified_time  timestamptz       not null,
    modified_by    bigint            not null
) with (autovacuum_enabled = true);

alter table branches
    add constraint branches_branch_id_pk primary key (branch_id);

alter table branches
    add constraint branches_repository_id_branch_name_uq unique (repository_id, branch_name);