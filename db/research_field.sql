/* research_field table */
ALTER TABLE research_field
    AUTO_INCREMENT = 1;

DROP TABLE research_field;

CREATE TABLE research_field
(
    idx                   bigint auto_increment primary key,
    created_by            varchar(255) null,
    created_date          datetime     null,
    last_modified_by      varchar(255) null,
    last_modified_date    datetime     null,
    active_status         varchar(255) null,
    views                 bigint       null,

    title                 varchar(255) null,
    content               mediumtext   null,
    research_field        varchar(255) null,
    research_field_status varchar(255) null
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;