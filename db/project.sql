/* project table */
CREATE TABLE project
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
    project_status          varchar(255)        null,
    research_establishment  varchar(255)        null,
    start_date              datetime(6)         null,
    end_date                datetime(6)         null
);

ALTER TABLE project AUTO_INCREMENT=1;
DROP TABLE project;
