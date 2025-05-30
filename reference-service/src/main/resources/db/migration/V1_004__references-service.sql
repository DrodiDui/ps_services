insert into references_type(reference_type, description)
values ('TASK_TYPE', '{"ru": "Тип задачи"}');

insert into reference_item(reference_item_id, reference_type, item_code, description)
values (30, 'TASK_TYPE', 'FEATURES', '{"ru": "функции"}'),
       (31, 'TASK_TYPE', 'BAG', '{"ru": "Ошибка"}'),
       (32, 'TASK_TYPE', 'DOCUMENTATION', '{"ru": "Документация"}');