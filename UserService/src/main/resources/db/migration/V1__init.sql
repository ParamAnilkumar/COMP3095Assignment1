-- v1__init.sql
CREATE TABLE t_users (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL UNIQUE,
   role VARCHAR(255) NOT NULL,
   student BOOLEAN DEFAULT FALSE
    );

-- Optional: Insert some initial data
INSERT INTO t_users (name, email, role, student) VALUES
                                                       ('Param', 'param@example.com', 'Admin', TRUE),
                                                       ('Ayush', 'ayush@example.com', 'User', FALSE),
                                                       ('vedant','vedant','User',TRUE);
