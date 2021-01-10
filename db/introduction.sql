/* introduction table */
ALTER TABLE introduction
    AUTO_INCREMENT = 1;

DROP TABLE introduction;

CREATE TABLE introduction
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
);