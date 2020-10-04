/* seminar table */
CREATE TABLE seminar
(
    idx                     bigint              auto_increment    primary key,
    created_by              varchar(255)        null,
    created_date            datetime(6)         null,
    last_modified_by        varchar(255)        null,
    last_modified_date      datetime(6)         null,
    active_status           varchar(255)        null,
    title                   varchar(255)        null,
    seminar_type            varchar(255)        null,
    presentation_date       datetime(6)         null,
    place                   varchar(255)        null,
    presenter               varchar(255)        null,
    category_idx            bigint              null,
    content                 longtext            null,
    views                   bigint              null
);

ALTER TABLE seminar AUTO_INCREMENT=1;
DROP TABLE seminar;

