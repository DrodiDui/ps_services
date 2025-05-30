insert into authorities (authority_id, authority_code, description)
VALUES (12, 'VIEW_REPOSITORY', ''),
       (13, 'ADD_REPOSITORY', '');

insert into roles_authorities (role_id, authority_id)
VALUES (1, 12),
       (2, 12),
       (3, 12),
       (4, 12),
       (5, 12);

insert into roles_authorities (role_id, authority_id)
VALUES (1, 13),
       (2, 13),
       (3, 13),
       (4, 13),
       (5, 13);