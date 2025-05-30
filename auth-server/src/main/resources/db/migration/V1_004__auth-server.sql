insert into roles (role_id, role_code)
VALUES (1, 'MAINTAINER'),
       (2, 'DEVELOPER'),
       (3, 'TESTER'),
       (4, 'ANALYST'),
       (5, 'PROJECT_MANAGER');

insert into authorities (authority_id, authority_code, description)
VALUES (1, 'VIEW_PROJECT', ''),
       (2, 'ADD_PROJECT', ''),
       (3, 'VIEW_SPACE', ''),
       (4, 'ADD_SPACE', ''),
       (5, 'VIEW_EMPLOYEES', ''),
       (6, 'ADD_EMPLOYEES', ''),
       (7, 'ADD_EMPLOYEE_TO_PROJECT', ''),
       (8, 'VIEW_TASKS', ''),
       (9, 'ADD_TASKS', '');


insert into roles_authorities (role_id, authority_id)
VALUES (1, 1),
       (1, 2) ,
       (1, 3) ,
       (1, 4) ,
       (1, 5) ,
       (1, 6) ,
       (1, 7) ,
       (1, 8) ,
       (1, 9);

insert into roles_authorities (role_id, authority_id)
VALUES (2, 1),
       (2, 2) ,
       (2, 3) ,
       (2, 4) ,
       (2, 5) ,
       (2, 6) ,
       (2, 7) ,
       (2, 8) ,
       (2, 9);

insert into roles_authorities (role_id, authority_id)
VALUES (3, 1),
       (3, 2) ,
       (3, 3) ,
       (3, 4) ,
       (3, 5) ,
       (3, 6) ,
       (3, 7) ,
       (3, 8) ,
       (3, 9);

insert into roles_authorities (role_id, authority_id)
VALUES (4, 1),
       (4, 2) ,
       (4, 3) ,
       (4, 4) ,
       (4, 5) ,
       (4, 6) ,
       (4, 7) ,
       (4, 8) ,
       (4, 9);

insert into roles_authorities (role_id, authority_id)
VALUES (5, 1),
       (5, 2) ,
       (5, 3) ,
       (5, 4) ,
       (5, 5) ,
       (5, 6) ,
       (5, 7) ,
       (5, 8) ,
       (5, 9);
