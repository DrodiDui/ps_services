insert into references_type(reference_type, description)
values ('EMPLOYEE_ROLE', '{"ru": "Тип задачи"}'),
       ('EMPLOYEE_STATUS', '{"ru": "Тип задачи"}');

insert into reference_item(reference_item_id, reference_type, item_code, description)
values (100, 'EMPLOYEE_ROLE', 'DEVELOPER', '{"ru": "Разработчик"}'),
       (101, 'EMPLOYEE_ROLE', 'TESTER', '{"ru": "Тестировщик"}'),
       (102, 'EMPLOYEE_ROLE', 'ANALYST', '{"ru": "Аналитик"}'),
       (103, 'EMPLOYEE_ROLE', 'PROJECT_MANGER', '{"ru": "Проектный менеджер"}');

insert into reference_item(reference_item_id, reference_type, item_code, description)
values (200, 'EMPLOYEE_STATUS', 'ACTIVE', '{"ru": "Активный"}'),
       (201, 'EMPLOYEE_STATUS', 'IN_ACTIVE', '{"ru": "Не активный"}'),
       (202, 'EMPLOYEE_STATUS', 'DELETE', '{"ru": "Удалён"}');