insert into reference_types (reference_type , description)
values ('PROJECT_TYPE', '{"ru": "Тип проекта", "en": "Project type"}');

insert into reference_items(reference_type, item_code, description, is_active)
values
    ('PROJECT_TYPE', 'BACKEND', '{"ru": "бэкенд"}', true),
    ('PROJECT_TYPE', 'FRONTEND', '{"ru": "фронтенд"}', true),
    ('PROJECT_TYPE', 'DESKTOP', '{"ru": "десктоп"}', true),
    ('PROJECT_TYPE', 'MOBILE', '{"ru": "мобильный"}', true);


insert into reference_metadata(workspace_id, reference_item_id, metadata_name, metadata)
VALUES
    (null, 1, 'ICON_TAG', '{"icon": "Server", "color": "purple"}'),
    (null, 2, 'ICON_TAG', '{"icon": "LayoutTemplate", "color": "blue"}'),
    (null, 3, 'ICON_TAG', '{"icon": "Monitor", "color": "green"}'),
    (null, 4, 'ICON_TAG', '{"icon": "Smartphone", "color": "red"}');




insert into reference_types(reference_type, description)
VALUES ('MEMBER_POSITION', '{"ru": "Позиция сотрудника"}'),
       ('MEMBER_STATUS', '{"ru": "Позиция сотрудника"}'),
       ('TASK_STATUS', '{"ru": "Позиция сотрудника"}'),
       ('TASK_PRIORITY', '{"ru": "Позиция сотрудника"}'),
       ('TASK_TYPE', '{"ru": "Позиция сотрудника"}');


insert into reference_items (reference_type, item_code, description, is_active)
VALUES ('MEMBER_POSITION', 'DEVELOPER', '{"ru": "Разработчик"}', true),
       ('MEMBER_POSITION', 'TESTER', '{"ru": "Тестировщик"}', true),
       ('MEMBER_POSITION', 'TEAM_LEAD', '{"ru": "Тимлид"}', true),
       ('MEMBER_POSITION', 'TEAM_ARCHITECT', '{"ru": "Архитектор"}', true),
       ('MEMBER_POSITION', 'ANALYST', '{"ru": "Аналитик"}', true),
       ('MEMBER_POSITION', 'PROJECT_MANAGER', '{"ru": "Проектный менеджер"}', true);

insert into reference_items (reference_type, item_code, description, is_active)
VALUES ('MEMBER_STATUS', 'ACTIVE', '{"ru": "Активен"}', true),
       ('MEMBER_STATUS', 'HIDDEN', '{"ru": "Скрыт"}', true),
       ('MEMBER_STATUS', 'DELETE', '{"ru": "Удалён"}', true);

insert into reference_items (reference_type, item_code, description, is_active)
VALUES ('TASK_STATUS', 'ACTIVE', '{"ru": "Активена"}', true),
       ('TASK_STATUS', 'IN_DEVELOPMENT', '{"ru": "В разработке"}', true),
       ('TASK_STATUS', 'IN_REVIEW', '{"ru": "На просмотре"}', true),
       ('TASK_STATUS', 'IN_TEST', '{"ru": "На тестировании"}', true),
       ('TASK_STATUS', 'DONE', '{"ru": "Выполнена"}', true),
       ('TASK_STATUS', 'CANCELED', '{"ru": "Отменена"}', true),
       ('TASK_STATUS', 'BACKLOG', '{"ru": "Backlog"}', true);

insert into reference_items (reference_type, item_code, description, is_active)
VALUES ('TASK_PRIORITY', 'LOW', '{"ru": "Низкий"}', true),
       ('TASK_PRIORITY', 'NORMAL', '{"ru": "Нормальный"}', true),
       ('TASK_PRIORITY', 'HIGH', '{"ru": "Высокий"}', true);

insert into reference_items (reference_type, item_code, description, is_active)
VALUES ('TASK_TYPE', 'FEATURE', '{"ru": "Функционал"}', true),
       ('TASK_TYPE', 'BAG', '{"ru": "Ошибка"}', true),
       ('TASK_TYPE', 'DOCUMENTATION', '{"ru": "Документация"}', true);