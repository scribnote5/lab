/* album table */
CREATE TABLE album
(
    idx                     bigint              auto_increment    primary key,
    created_by              varchar(255)        null,
    created_date            datetime(6)         null,
    last_modified_by        varchar(255)        null,
    last_modified_date      datetime(6)         null,
    active_status           varchar(255)        null,
    title                   varchar(255)        null,
    hash_tags               varchar(255)        null,
    main_hash_tag           varchar(255)        null,
    photo_taken_date        datetime(6)         null,
    main_page_priority      bigint              null
);

ALTER TABLE album AUTO_INCREMENT=1;
DROP TABLE album;
