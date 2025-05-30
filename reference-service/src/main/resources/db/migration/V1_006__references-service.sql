insert into references_type(reference_type, description)
values ('GIT_PROVIDER', '{"ru": "Тип git репозитория"}');


insert into reference_item(reference_item_id, reference_type, item_code, description)
values (210, 'GIT_PROVIDER', 'INTERNAL', '{"ru": "Репозиторий PsGit"}'),
       (211, 'GIT_PROVIDER', 'GITHUB', '{"ru": "Репозиторий GitHub"}'),
       (212, 'GIT_PROVIDER', 'GITLAB', '{"ru": "Репозиторий GitLab"}');