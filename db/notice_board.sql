/* notice_board table */
ALTER TABLE notice_board
    AUTO_INCREMENT = 1;

DROP TABLE notice_board;

CREATE TABLE notice_board
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime     null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime     null,
    active_status      varchar(255) null,
    views              bigint       null,

    content            mediumtext   null,
    title              varchar(255) null
);