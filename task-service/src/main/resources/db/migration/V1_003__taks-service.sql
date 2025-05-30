alter table tasks
    add column created_time timestamptz not null default current_timestamp;
alter table tasks
    add column created_by bigint not null default 0;
alter table tasks
    add modified_time timestamptz;
alter table tasks
    add column modified_by bigint;