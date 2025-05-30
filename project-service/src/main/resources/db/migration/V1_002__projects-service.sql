create table projects
(
    project_id          bigserial         not null,
    space_id            bigint            not null,
    title               character varying not null,
    description         character varying not null,
    project_status_id   bigint            not null,
    created_date        date              not null,
    expected_close_date date              null,
    created_time        timestamptz       not null,
    created_by          bigint            not null,
    modified_time       timestamptz       not null,
    modified_by         bigint            not null
) with (autovacuum_enabled = true);

alter table projects
    add constraint projects_project_id_pk primary key (project_id);

alter table projects
    add constraint projects_project_title_space_id_uq unique (title, space_id);

alter table projects
    add constraint project_space_id_fk foreign key (space_id) references spaces (space_id);


