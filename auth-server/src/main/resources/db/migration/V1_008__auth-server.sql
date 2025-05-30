insert into authorities (authority_id, authority_code, description)
VALUES (17, 'VIEW_CONTENT', '');


insert into roles_authorities (role_id, authority_id)
VALUES (1, 17),
       (2, 17),
       (3, 17),
       (4, 17),
       (5, 17);