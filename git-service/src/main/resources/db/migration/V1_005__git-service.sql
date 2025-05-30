create table settings
(
    setting_id       bigserial         not null,
    setting_name    character varying not null,
    repository_id    bigint            not null,
    provider_setting bigint            not null,
    settings         jsonb             not null
) with (autovacuum_enabled = true);

alter table settings
    add constraint settings_setting_id_pk primary key (setting_id);

alter table settings
    add constraint settings_repository_id_fk foreign key (repository_id) references repositories (repository_id);