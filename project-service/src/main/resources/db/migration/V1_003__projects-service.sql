create table employees
(
    employee_id        bigserial         not null,
    first_name         character varying not null,
    last_name          character varying not null,
    middle_name        character varying,
    email              character varying not null,
    employee_role_id   bigint            not null,
    employee_status_id bigint            not null,
    created_time       timestamptz       not null,
    created_by         bigint            not null,
    modified_time      timestamptz       not null,
    modified_by        bigint            not null
) with (autovacuum_enabled = true);

alter table employees add constraint employees_employee_id_pk primary key (employee_id);

alter table employees add constraint employees_email_uq unique (email);
