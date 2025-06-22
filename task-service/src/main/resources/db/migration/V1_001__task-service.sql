create table tasks
(
    task_id            bigserial         not null,
    task_name          character varying not null,
    task_title         character varying not null,
    description        character varying not null,
    owner_id           bigint            not null,
    assignee_id        bigint            not null,
    task_status_id     bigint            not null,
    task_priority_id   bigint            not null,
    project_id         bigint            not null,
    workspace_id       bigint            not null,
    created_date       timestamptz       not null,
    created_by         bigint            not null,
    last_modified_date timestamptz       not null,
    last_modified_by   bigint            not null
) with (autovacuum_enabled = true);

alter table tasks
    add constraint tasks_task_id_pk primary key (task_id);

create table project_task_counter
(
    project_id bigint not null,
    counter    bigint not null
) with (autovacuum_enabled = true);

alter table project_task_counter
    add constraint project_task_counter_project_id_pk primary key (project_id);

create table tags
(
    tag_id       bigserial         not null,
    tag_name     character varying not null,
    workspace_id bigint            not null
) with (autovacuum_enabled = true);

alter table tags
    add constraint tags_tag_id_pk primary key (tag_id);

create table task_tags
(
    tag_id  bigint not null,
    task_id bigint not null
) with (autovacuum_enabled = true);

alter table task_tags
    add constraint task_tags_task_tag_id primary key (task_id, tag_id);

alter table task_tags
    add constraint task_tags_task_id_fk foreign key (task_id) references tasks (task_id);
alter table task_tags
    add constraint task_tags_tag_id_fk foreign key (tag_id) references tags (tag_id);

