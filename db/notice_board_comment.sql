CREATE TABLE notice_board_comment
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    notice_board_idx   long             null,
    content            longtext         null
);

ALTER TABLE notice_board_comment AUTO_INCREMENT=1;
DROP TABLE notice_board_comment;
