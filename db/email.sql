/* email table */
ALTER TABLE email
    AUTO_INCREMENT = 1;

DROP TABLE email;

CREATE TABLE email
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime     null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime     null,
    active_status      varchar(255) null,
    views              bigint       null,

    email_address      varchar(255) null,
    note               mediumtext   null,
    receiver_type      varchar(255) null
);