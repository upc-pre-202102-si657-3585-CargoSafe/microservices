-- Script de inicialización de datos para Profile Service
-- Ejecutar después de que la base de datos esté lista

USE profile_service;

-- Insertar perfiles de prueba
INSERT INTO profiles (id, created_at, updated_at, email_address, street_address_street, street_address_number, street_address_city, street_address_postal_code, street_address_country, name_first_name, name_last_name, user_id) VALUES
(1, NOW(), NOW(), 'john.doe@example.com', 'Calle Principal', '123', 'Madrid', '28001', 'España', 'John', 'Doe', 1),
(2, NOW(), NOW(), 'jane.smith@example.com', 'Avenida Central', '456', 'Barcelona', '08001', 'España', 'Jane', 'Smith', 2),
(3, NOW(), NOW(), 'mike.johnson@example.com', 'Plaza Mayor', '789', 'Valencia', '46001', 'España', 'Mike', 'Johnson', 3);

-- Verificar que los datos se insertaron correctamente
SELECT * FROM profiles; 