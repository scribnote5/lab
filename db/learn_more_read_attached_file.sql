/* learn_more_read_attached_file table */
ALTER TABLE learn_more_read_attached_file AUTO_INCREMENT=1;

DROP TABLE learn_more_read_attached_file;

CREATE TABLE learn_more_read_attached_file
(
    idx                 bigint auto_increment primary key,
    created_by          varchar(255) null,
    created_date        datetime null,
    file_datetime       varchar(255) null,
    saved_file_datetime varchar(255) null,
    learn_more_read_idx bigint null,
    file_size           varchar(255) null
);