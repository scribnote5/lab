/* publication table */
ALTER TABLE publication AUTO_INCREMENT=1;

DROP TABLE publication;

CREATE TABLE publication
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255) null,
    created_date       datetime null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime null,
    active_status      varchar(255) null,
    views              bigint null,

    title              varchar(255) null,
    authors            varchar(255) null,
    publication_type   varchar(255) null,
    publishing_area    varchar(255) null,
    published_in       varchar(255) null,
    impact_factor      varchar(255) null,
    published_date     datetime null,
    pages              varchar(255) null,
    volume             varchar(255) null,
    number             varchar(255) null,
    doi                varchar(255) null,
    uri                varchar(255) null,
    isbn_issn          varchar(255) null,
    remark             varchar(255) null
);