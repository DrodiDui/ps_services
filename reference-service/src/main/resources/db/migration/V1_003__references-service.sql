insert into references_type(reference_type, description)
values ('TASK_STATUS', '{"ru": "Статус задачи"}'),
       ('TASK_PRIORITY', '{"ru": "Приоритет задачи"}');

insert into reference_item(reference_item_id, reference_type, item_code, description)
values (10, 'TASK_STATUS', 'TODO', '{"ru": "Todo"}'),
       (11, 'TASK_STATUS', 'IN_PROGRESS', '{"ru": "Выполннятся"}'),
       (12, 'TASK_STATUS', 'IN_TEST', '{"ru": "На тстировании"}'),
       (13, 'TASK_STATUS', 'IN_REVIEW', '{"ru": "На просмотре"}'),
       (14, 'TASK_STATUS', 'DONE', '{"ru": "Выполнена"}'),
       (15, 'TASK_STATUS', 'CANCELED', '{"ru": "Отменена"}'),
       (16, 'TASK_STATUS', 'BACKLOG', '{"ru": "Backlog"}');

insert into reference_item(reference_item_id, reference_type, item_code, description)
VALUES (20, 'TASK_PRIORITY', 'LOW', '{"ru": "Низкий"}'),
       (21, 'TASK_PRIORITY', 'MEDIUM', '{"ru": "Нормальный"}'),
       (22, 'TASK_PRIORITY', 'HIGH', '{"ru": "Высокий"}');