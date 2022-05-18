create table users(
    id           bigint auto_increment primary key,
    date_created TIMESTAMP      NOT NULL,
    username     VARCHAR(255)   NOT NULL,
    password     VARCHAR(255)   NOT NULL,
    enabled      BOOL,
    CONSTRAINT UK_username unique (username)
);


