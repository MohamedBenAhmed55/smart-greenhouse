INSERT INTO equipment (type, name, active)
VALUES
    ('FAN', 'Main Exhaust Fan', false),
    ('HEATER', 'Central Heater', false),
    ('WATER_PUMP', 'Irrigation Pump', false),
    ('LIGHT_SYSTEM', 'Growth Lights', true)
    ON CONFLICT DO NOTHING;