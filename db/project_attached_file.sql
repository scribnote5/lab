/* project_attached_file table */
ALTER TABLE project_attached_file
    AUTO_INCREMENT = 1;

DROP TABLE project_attached_file;

CREATE TABLE project_attached_file
(
    idx             bigint auto_increment primary key,
    created_by      varchar(255) null,
    created_date    datetime(6)  null,
    file_name       varchar(255) null,
    saved_file_name varchar(255) null,
    project_idx     bigint       null,
    file_size       varchar(255) null
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;