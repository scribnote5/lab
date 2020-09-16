/* user table */
CREATE TABLE user
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255)     null,
    created_date       datetime         null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime         null,
    active_status      varchar(255)     null,
    admission_date     date             null,
    authority_type     varchar(255)     null,
    birth_date         date             null,
    contact            varchar(255)     null,
    email              varchar(255)     null,
    private_email      varchar(255)     null,
    english_name       varchar(255)     null,
    gender             varchar(255)     null,
    graduated_date     date             null,
    introduction       longtext         null,
    korean_name        varchar(255)     null,
    username           varchar(255)     null,
    user_status        varchar(255)     null,
    user_type          varchar(255)     null,
    messenger_id       varchar(255)     null,
    password           varchar(255)     null,
    web_page           varchar(255)     null,
    workplace          varchar(255)     null
);

ALTER TABLE user AUTO_INCREMENT=1;
DROP TABLE user;