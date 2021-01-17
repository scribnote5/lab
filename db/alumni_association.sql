/* alumni_association table */
ALTER TABLE alumni_association
    AUTO_INCREMENT = 1;

DROP TABLE alumni_association;

CREATE TABLE alumni_association
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime     null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime     null,
    active_status      varchar(255) null,
    views              bigint       null,

    title              varchar(255) null,
    main_page_priority bigint       null,
    content            mediumtext   null
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;