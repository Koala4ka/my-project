INSERT INTO permission_table (name, description)
VALUES ('User-Add','Creating Users'),
('User-Edit','Edit Users'),
('User-Delete','Delete Users'),
('Edit-Info','Can edit all information'),
('View','Only watch and cant edit info');

-- INSERT INTO permission_role (permission_id, role_id)
-- VALUES ((SELECT id
--          FROM permission_table
--          WHERE name IN ('User-Add','User-Edit','User-Delete','Edit-Info','View')),
--         (SELECT id
--          FROM roles
--          WHERE roles.name = 'MASTER'));

INSERT INTO permission_role  (permission_id, role_id)
SELECT  permissionId.id, roleId.id
FROM (SELECT id FROM permission_table
WHERE name in ('User-Add','User-Edit','User-Delete','Edit-Info','View'))
    AS permissionId
         CROSS JOIN
     (SELECT id FROM roles WHERE roles.name = 'MASTER')
         AS roleId
         ON CONFLICT DO NOTHING;

-- INSERT
-- INTO role_user (user_id, role_id)
-- VALUES ((SELECT id
--          FROM permission_table
--          WHERE name IN ('Edit-Info','View')),
--         (SELECT id
--          FROM roles
--          WHERE roles.name = 'MANAGER'))









