/* learn_more_video_attached_file table */
ALTER TABLE learn_more_video_attached_file
    AUTO_INCREMENT = 1;

DROP TABLE learn_more_video_attached_file;

CREATE TABLE learn_more_video_attached_file
(
    idx                  bigint auto_increment primary key,
    created_by           varchar(255) null,
    created_date         datetime     null,
    file_name            varchar(255) null,
    saved_file_name      varchar(255) null,
    learn_more_video_idx bigint       null,
    file_size            varchar(255) null
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;