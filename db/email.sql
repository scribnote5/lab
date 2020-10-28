/* email table */
CREATE TABLE email
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    email_address      varchar(255)     null,
    note               longtext         null,
    receiver_type      varchar(255)     null
);

ALTER TABLE email AUTO_INCREMENT=1;
DROP TABLE email;
