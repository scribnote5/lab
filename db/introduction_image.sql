/* introduction_image table */
CREATE TABLE introduction_image
(
    idx                     bigint              auto_increment    primary key,
    created_by              varchar(255)        null,
    created_date            datetime(6)         null,
    last_modified_by        varchar(255)        null,
    last_modified_date      datetime(6)         null,
    active_status           varchar(255)        null,
    title                   varchar(255)        null,
    main_page_priority      bigint              null
);

ALTER TABLE introduction_image AUTO_INCREMENT=1;
DROP TABLE introduction_image;
