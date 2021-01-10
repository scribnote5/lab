/* user table */
ALTER TABLE user
    AUTO_INCREMENT = 1;

DROP TABLE user;

CREATE TABLE user
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime     null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime     null,
    active_status      varchar(255) null,
    views              bigint       null,

    username           varchar(255) null,
    password           varchar(255) null,
    korean_name        varchar(255) null,
    english_name       varchar(255) null,
    user_type          varchar(255) null,
    user_status        varchar(255) null,

    introduction       mediumtext   null,

    admission_date     date         null,
    graduated_date     date         null,
    gender             varchar(255) null,
    birth_date         date         null,
    workplace          varchar(255) null,

    contact            varchar(255) null,
    messenger_id       varchar(255) null,
    email              varchar(255) null,
    private_email      varchar(255) null,
    github             varchar(255) null,
    linked_in          varchar(255) null,
    external_web_page  varchar(255) null,

    authority_type     varchar(255) null
);

/* don't change idx(primary key) */
INSERT INTO user (idx, created_by, created_date, last_modified_by, last_modified_date, active_status, views, username, password, korean_name, english_name, user_type, user_status, introduction, admission_date, graduated_date, gender, birth_date, workplace, contact, messenger_id, email, private_email, github, linked_in, external_web_page, authority_type)
VALUES (1, 'root', current_time, 'root', current_time, 'ACTIVE', 0, 'root', '$2a$10$Zg/EzcY/nIqHSPjU3eFpuOopz4Y/1H3LwvVxXhNkvNlni4AOsQ1CW', 'root', 'root', 'PART_TIME_MS', null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, 'ROOT');