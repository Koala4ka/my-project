INSERT INTO roles (name, description)
VALUES ('MASTER',
        'Creating Users,Manage everything');
INSERT INTO roles (name, description)
VALUES ('MANAGER',
        'Manage everything but cant create users');
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