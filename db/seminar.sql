/* seminar table */
ALTER TABLE seminar
    AUTO_INCREMENT = 1;

DROP TABLE seminar;

CREATE TABLE seminar
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime     null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime     null,
    active_status      varchar(255) null,
    views              bigint       null,

    title              varchar(255) null,
    seminar_type       varchar(255) null,
    presentation_date  datetime     null,
    place              varchar(255) null,
    presenter          varchar(255) null,
    category_idx       bigint       null,
    content            mediumtext   null
);