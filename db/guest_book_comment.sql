/* guest_book_comment table */
ALTER TABLE guest_book_comment
    AUTO_INCREMENT = 1;

DROP TABLE guest_book_comment;

CREATE TABLE guest_book_comment
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime     null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime     null,
    active_status      varchar(255) null,
    views              bigint       null,

    guest_book_idx   long         null,
    content            mediumtext   null
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;