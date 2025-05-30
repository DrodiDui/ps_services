insert into authorities (authority_id, authority_code, description)
VALUES (10, 'VIEW_REFERENCE', ''),
       (11, 'EDIT_TASK', '');

insert into roles_authorities (role_id, authority_id)
VALUES (1, 10),
       (2, 10),
       (3, 10),
       (4, 10),
       (5, 10);

insert into roles_authorities (role_id, authority_id)
VALUES (1, 11),
       (2, 11),
       (3, 11),
       (4, 11),
       (5, 11);