-- Initial Limits for Sensors (Parameter Table)
INSERT INTO parameter (type, min_threshold, max_threshold)
VALUES
    ('TEMPERATURE', 18.0, 28.0),
    ('HUMIDITY', 40.0, 70.0),
    ('LUMINOSITY', 300.0, 1000.0),
    ('SOIL_MOISTURE', 30.0, 80.0)
    ON CONFLICT DO NOTHING;

-- Dummy Measurements (Optional: So graphs aren't empty)
INSERT INTO measurement (type, value, timestamp, parameter_id)
VALUES
    ('TEMPERATURE', 22.5, NOW(), 1),
    ('HUMIDITY', 45.0, NOW(), 2);