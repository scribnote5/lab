/* login_history table */
ALTER TABLE login_history
    AUTO_INCREMENT = 1;

DROP TABLE login_history;

CREATE TABLE login_history
(
    idx                   bigint auto_increment primary key,
    created_by            varchar(255) null,
    created_date          datetime     null,
    last_modified_by      varchar(255) null,
    last_modified_date    datetime     null,
    active_status         varchar(255) null,
    views                 bigint       null,

    aud_idx               bigint,
    aud_ip                varchar(255),
    aud_location          varchar(255),
    aud_message           varchar(255),
    aud_sub_message       varchar(255),
    aud_login_result_type varchar(255)
);