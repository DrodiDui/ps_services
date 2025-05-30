insert into clients(id, client_id, client_id_issued_at, client_secret, client_secret_expires_at, client_name,
                    client_authentication_methods, authorization_grant_types, scopes, client_settings, token_settings)
values (nextval('clients_id_seq'), 'demo-client', now(), '$2y$10$jp/Kz0dcHEX7mNJby1Ix6emWtQ29oOs4hE9Dd3h8T3vWR6A1AOuW.',
        '2025-12-31 23:59:59.000 + 03:00',
        'demo-client', 'client_secret_basic', 'password refresh_token', 'read, openId', '{}',
        '{"access-token-time-to-live": 3600, "refresh-token-time-to-live": 86400}');


insert into user_statuses(status_id, status_name, description, is_active)
VALUES (100000, 'ACTIVE', 'Активный', true),
       (100001, 'BLOCKED', 'Заблокированный', true),
       (100002, 'NO_ACTIVE', 'Не активный', true);
