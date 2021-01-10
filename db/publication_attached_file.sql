/* publication_attached_file table */
ALTER TABLE publication_attached_file AUTO_INCREMENT=1;

DROP TABLE publication_attached_file;

CREATE TABLE publication_attached_file
(
    idx             bigint auto_increment primary key,
    created_by      varchar(255) null,
    created_date    datetime null,
    file_name       varchar(255) null,
    saved_file_name varchar(255) null,
    publication_idx bigint null,
    file_size       varchar(255) null
);