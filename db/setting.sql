/* setting table */
ALTER TABLE setting
    AUTO_INCREMENT = 1;

DROP TABLE setting;

CREATE TABLE setting
(
    idx                                 bigint auto_increment primary key,
    created_by                          varchar(255) null,
    created_date                        datetime     null,
    last_modified_by                    varchar(255) null,
    last_modified_date                  datetime     null,
    active_status                       varchar(255) null,
    views                               bigint       null,

    lab_address                         varchar(255) null,
    email_recipient                     varchar(255) null,
    email_address                       varchar(255) null,
    callee                              varchar(255) null,
    phone_number                        varchar(255) null,
    lab_start_date                      date         null,
    lab_maintenance_years_count_content varchar(255) null,
    user_count_content                  varchar(255) null,
    publication_count_content           varchar(255) null,
    project_count_content               varchar(255) null
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

/* don't change idx(primary key) */
INSERT INTO setting
(idx, created_by, created_date, last_modified_by, last_modified_date, active_status, lab_address, email_recipient, email_address, callee, phone_number, lab_start_date)
VALUES (1, 'root', current_time, 'root', current_time, 'ACTIVE', '', '', '', '', '', current_date);