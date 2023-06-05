INSERT INTO payment_modes (payment_mode, payment_short, description, status, created_at, updated_at)
VALUES ('Cash on Hand', 'CASH', 'Pay by Cash at the end of the ride', 1, current_timestamp, current_timestamp),
       ('VISA Card', 'VISA', 'Pay by VISA Card at the beginning of the ride', 0, current_timestamp, current_timestamp),
       ('Master Card', 'Master', 'Pay by Master Card at the beginning of the ride', 0, current_timestamp,
        current_timestamp);

INSERT INTO vehicle_types (vehicle_type, vehicle_short, seat_count, description, status, created_at, updated_at)
VALUES ('Motor Bike', 'Bike', 1, 'Travel behind a motor bike', 1, current_timestamp, current_timestamp),
       ('Three Wheeler', 'Tuk', 3, 'A minimal cost effective 3 seater vehicle', 1, current_timestamp,
        current_timestamp),
       ('Mini Car', 'Mini', 4, 'A compact cost effective 4 seater', 1, current_timestamp, current_timestamp),
       ('Luxury Car', 'Lux', 4, 'A spacious luxury 4 seater', 1, current_timestamp, current_timestamp),
       ('Mega Ride', 'Mega', 7, 'A 7 seater group/cargo vehicle', 1, current_timestamp, current_timestamp);