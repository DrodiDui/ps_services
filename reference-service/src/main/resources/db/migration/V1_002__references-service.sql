insert into references_type(reference_type, description)
values ('PROJECT_STATUS', '{"ru": "Статус проекта"}');

insert into reference_item(reference_item_id, reference_type, item_code, description)
values (1, 'PROJECT_STATUS', 'ACTIVE', '{"ru": "Активный"}'),
       (2, 'PROJECT_STATUS', 'CLOSE', '{"ru": "Закрат"}'),
       (3, 'PROJECT_STATUS', 'EXPIRED', '{"ru": "Просрочен"}');