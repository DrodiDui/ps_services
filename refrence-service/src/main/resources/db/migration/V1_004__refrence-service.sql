insert into reference_types (reference_type , description)
values ('GIT_PROVIDER', '{"ru": "Git провайдер", "en": "Git provider"}');

insert into reference_items(reference_type, item_code, description, is_active)
values
    ('GIT_PROVIDER', 'GIT_PS', '{"ru": "GitPs"}', true),
    ('GIT_PROVIDER', 'GIT_HUB', '{"ru": "GitHub"}', true),
    ('GIT_PROVIDER', 'GIT_LAB', '{"ru": "GitLab"}', true);