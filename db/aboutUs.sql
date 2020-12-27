/* aboutUs table */
CREATE TABLE about_us
(
    idx                     bigint              auto_increment    primary key,
    created_by              varchar(255)        null,
    created_date            datetime(6)         null,
    last_modified_by        varchar(255)        null,
    last_modified_date      datetime(6)         null,
    active_status           varchar(255)        null,
    content                 longtext            null,
    title                   varchar(255)        null,
    views                   bigint              null,
    research_field_idx      bigint              null,
    aboutUs_status          varchar(255)        null,
    research_establishment  varchar(255)        null,
    start_date              datetime(6)         null,
    end_date                datetime(6)         null
);

ALTER TABLE about_us AUTO_INCREMENT=1;
DROP TABLE about_us;
