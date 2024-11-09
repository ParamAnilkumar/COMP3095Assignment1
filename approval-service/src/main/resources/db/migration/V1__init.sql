CREATE TABLE t_approvals (
  id BIGSERIAL NOT NULL,
  event_id VARCHAR(255) NULL,
  user_id VARCHAR(255)  NULL,
  approved BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);