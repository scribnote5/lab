/* notice_board_attached_file.sql table */
CREATE TABLE notice_board_attached_file
(
    idx               bigint auto_increment    primary key,
    created_by        varchar(255)    null,
    created_date      datetime(6)     null,
    file_name         varchar(255)    null,
    saved_file_name   varchar(255)    null,
    notice_board_idx  bigint          null,
    file_size         varchar(255)    null
);

ALTER TABLE notice_board_attached_file AUTO_INCREMENT=1;
DROP TABLE notice_board_attached_file;