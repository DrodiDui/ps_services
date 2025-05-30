create table tasks
(
    task_id          bigserial         not null,
    name             character varying not null,
    title            character varying not null,
    description      character varying not null,
    project_id       bigint            not null,
    task_status_id   bigint            not null,
    task_priority_id bigint            not null,
    start_date       date              not null,
    due_date         date,
    owner_id         bigint            not null,
    assignee_id      bigint            not null
) with (autovacuum_enabled = true);

alter table tasks
    add constraint tasks_task_id primary key (task_id);

create table task_counter
(
    project_id   bigint not null,
    task_counter bigint not null
) with (autovacuum_enabled = true);

alter table task_counter
    add constraint task_counter_project_id_pk primary key (project_id);
