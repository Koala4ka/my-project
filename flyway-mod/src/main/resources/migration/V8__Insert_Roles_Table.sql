INSERT INTO roles (name, description)
VALUES ('MASTER',
        'Creating Users,Manage everything');
INSERT INTO roles (name, description)
VALUES ('MANAGER',
        'Manage everything but cant create users');
INSERT INTO role_user (userId, roleId)
VALUES (SELECT id
               FROM users
               WHERE email = 'sara@dictumauguemalesuada.com'),
               (SELECT id
                FROM roles
                WHERE roles.name = 'MASTER'))
