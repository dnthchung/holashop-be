-- Insert initial roles data
-- This migration adds the default USER and ADMIN roles required by the application

INSERT INTO roles (id, name)
VALUES (1, 'USER'),
       (2, 'ADMIN')
ON DUPLICATE KEY UPDATE name=VALUES(name);
