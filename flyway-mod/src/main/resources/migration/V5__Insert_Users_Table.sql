INSERT INTO "users" (organization_id, email, login, password, phone)
VALUES ((SELECT id
         FROM organization
         WHERE organization.name = 'Apple'),
        'master@mail.com',
        'Quamar',
        '$2a$12$ZKbJ15SWtQxBwo3JWoIOtehf0usxjlxDTD8JNrVTmpnkyiIrmFdWS',
        '0985789678');


INSERT INTO "users" (organization_id,email, login, password, phone)
VALUES ((SELECT id
         FROM organization
         WHERE organization.name = 'ATB'),
        'manager@mail.com',
        'Sara',
        '$2a$12$qosSYY01GAgBMvXEiXlw7Oi/7gfH7UIdKOcw3xVQGbNkmVtegYyqG',
        '0985785678');
