insert into authorities (authority_id, authority_code, description)
VALUES (14, 'VIEW_BRANCHES', ''),
       (15, 'ADD_BRANCHES', ''),
       (16, 'VIEW_COMMITS', '');

insert into roles_authorities (role_id, authority_id)
VALUES (1, 14),
       (2, 14),
       (3, 14);

insert into roles_authorities (role_id, authority_id)
VALUES (1, 15),
       (2, 15);

insert into roles_authorities (role_id, authority_id)
VALUES (1, 16),
       (2, 16),
       (3, 16);