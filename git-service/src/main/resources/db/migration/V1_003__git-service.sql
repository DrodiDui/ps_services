create table commits
(
    commit_id    bigserial         not null,
    branch_id    bigint            not null,
    commit       character varying not null,
    message      character varying not null,
    commit_time timestamptz       not null,
    commit_author   bigint            not null
) with (autovacuum_enabled = true);

alter table commits
    add constraint commits_commit_id primary key (commit_id);

alter table commits
    add constraint commits_commit_id_branch_id_uq unique (commit_id, branch_id);