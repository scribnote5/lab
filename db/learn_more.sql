/* learn_more.sql table */
CREATE TABLE learn_more
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    title              varchar(255)     null,
    download_file_type varchar(255)     null
);

ALTER TABLE learn_more AUTO_INCREMENT=1;
DROP TABLE learn_more;
