/* learn_more_video.sql table */
CREATE TABLE learn_more_video
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    title              varchar(255)     null
);

ALTER TABLE learn_more_video AUTO_INCREMENT=1;
DROP TABLE learn_more_video;
