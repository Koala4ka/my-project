INSERT INTO roles (name, description, has_global_access)
VALUES ('MASTER',
        'Creating Users,Manage everything',
            TRUE);
INSERT INTO roles (name, description,has_global_access)
VALUES ('MANAGER',
        'Manage everything but cant create users',
        FALSE);
INSERT INTO role_user (user_id, role_id)
VALUES ((SELECT id
         FROM users
         WHERE email = 'master@mail.com'),
        (SELECT id
         FROM roles
         WHERE roles.name = 'MASTER'));
INSERT
INTO role_user (user_id, role_id)
VALUES ((SELECT id
         FROM users
         WHERE email = 'manager@mail.com'),
        (SELECT id
         FROM roles
         WHERE roles.name = 'MANAGER'))