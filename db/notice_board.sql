/* notice_board table */
CREATE TABLE notice_board
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    content            longtext         null,
    title              varchar(255)     null,
    views              bigint           null
);

ALTER TABLE notice_board AUTO_INCREMENT=1;
DROP TABLE notice_board;

show tables;