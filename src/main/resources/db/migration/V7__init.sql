CREATE TABLE users
(
    id           INT            NOT NULL,
    date_created TIMESTAMP      NOT NULL,
    username     VARCHAR(255)   NOT NULL,
    password     VARCHAR(255)   NOT NULL,
    enabled      BOOLEAN        NOT NULL
    CONSTRAINT contraint_id PRIMARY KEY (id)
);


ALTER TABLE users
ADD CONSTRAINT UC_username UNIQUE (username);