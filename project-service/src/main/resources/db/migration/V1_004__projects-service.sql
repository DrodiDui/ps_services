create table space_employees
(
    space_id            bigint not null,
    employee_id         bigint not null,
    employee_space_role bigint not null
) with (autovacuum_enabled = true);

alter table space_employees
    add constraint space_employees_space_employee_pk primary key (space_id, employee_id);

alter table space_employees
    add constraint space_employees_space_id_fk
        foreign key (space_id) references spaces (space_id);

alter table space_employees
    add constraint space_employees_employee_id_fk
        foreign key (employee_id) references employees (employee_id);
