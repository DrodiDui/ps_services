create table clients
(
    id                            bigserial                              not null,
    client_id                     varchar(255)                           not null,
    client_id_issued_at           timestamp    default CURRENT_TIMESTAMP not null,
    client_secret                 varchar(255) default null,
    client_secret_expires_at      timestamp    default null,
    client_name                   varchar(255)                           not null,
    client_authentication_methods varchar(1000)                          not null,
    authorization_grant_types     varchar(1000)                          not null,
    scopes                        varchar(1000)                          not null,
    client_settings               jsonb                                  not null,
    token_settings                jsonb                                  not null
) with (autovacuum_enabled = true);

alter table clients
    add constraint clients_id_pk primary key (id);

create table authorizations
(
    authorization_id         varchar(255) not null,
    registered_client_id     bigint       not null,
    authorization_grant_type varchar(255) not null,
    authorized_scopes        varchar(1000) default null,
    attributes               varchar(4000) default null,
    access_token_value       varchar(4000) default null,
    access_token_issued_at   timestamp     default null,
    access_token_expires_at  timestamp     default null,
    access_token_metadata    varchar(2000) default null,
    access_token_scopes      varchar(1000) default null,
    refresh_token_value      varchar(4000) default null,
    refresh_token_issued_at  timestamp     default null,
    refresh_token_expires_at timestamp     default null,
    refresh_token_metadata   varchar(2000) default null
) with (autovacuum_enabled = true);

alter table authorizations
    add constraint authorizations_authorization_id_pk primary key (authorization_id);
create sequence authorizations_authorization_id_seq start 100000 owned by authorizations.authorization_id;