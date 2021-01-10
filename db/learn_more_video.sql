/* learn_more_video.sql table */
ALTER TABLE learn_more_video
    AUTO_INCREMENT = 1;

DROP TABLE learn_more_video;

CREATE TABLE learn_more_video
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime     null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime     null,
    active_status      varchar(255) null,
    views              bigint       null,

    title              varchar(255) null
);