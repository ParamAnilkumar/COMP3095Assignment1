CREATE TABLE t_rooms(
    id BIGSERIAL NOT NULL,
    room_name VARCHAR(255) NULL,
    capacity INT,
    ac_room BOOLEAN DEFAULT FALSE,    -- Optional field with a default value of FALSE
    availability BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (id)
);