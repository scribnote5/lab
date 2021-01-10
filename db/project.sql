/* project table */
ALTER TABLE project
    AUTO_INCREMENT = 1;

DROP TABLE project;

CREATE TABLE project
(
    idx                    bigint auto_increment primary key,
    created_by             varchar(255) null,
    created_date           datetime     null,
    last_modified_by       varchar(255) null,
    last_modified_date     datetime     null,
    active_status          varchar(255) null,
    views                  bigint       null,

    title                  varchar(255) null,
    content                mediumtext   null,
    research_field_idx     bigint       null,
    project_status         varchar(255) null,
    research_establishment varchar(255) null,
    start_date             datetime     null,
    end_date               datetime     null
);