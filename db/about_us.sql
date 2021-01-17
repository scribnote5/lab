/* about_us table */
ALTER TABLE about_us
    AUTO_INCREMENT = 1;

DROP TABLE about_us;

CREATE TABLE about_us
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime     null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime     null,
    active_status      varchar(255) null,
    views              bigint       null,

    title              varchar(255) null,
    content            mediumtext   null
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;