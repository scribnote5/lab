/* introduction_image table */
ALTER TABLE introduction_image
    AUTO_INCREMENT = 1;

DROP TABLE introduction_image;

CREATE TABLE introduction_image
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime     null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime     null,
    active_status      varchar(255) null,
    views              bigint       null,

    title              varchar(255) null,
    main_page_priority bigint       null
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;