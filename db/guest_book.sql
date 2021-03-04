/* guest_book table */
ALTER TABLE guest_book
    AUTO_INCREMENT = 1;

DROP TABLE guest_book;

CREATE TABLE guest_book
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime     null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime     null,
    active_status      varchar(255) null,
    views              bigint       null,

    content            mediumtext   null,
    main_page_priority bigint       null,
    title              varchar(255) null
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;