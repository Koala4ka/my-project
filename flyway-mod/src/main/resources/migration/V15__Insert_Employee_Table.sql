INSERT INTO "employee_table" (organization_id, name, info)
VALUES ((SELECT id
         FROM organization
         WHERE organization.name = 'Apple'),
        'Vasya',
        'hr');














