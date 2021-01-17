/* notice_board_comment table */
ALTER TABLE notice_board_comment
    AUTO_INCREMENT = 1;

DROP TABLE notice_board_comment;

CREATE TABLE notice_board_comment
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime     null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime     null,
    active_status      varchar(255) null,
    views              bigint       null,

    notice_board_idx   long         null,
    content            mediumtext   null
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;