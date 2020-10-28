/* data_history table */
CREATE TABLE data_history
(
    idx                     bigint              auto_increment    primary key,
    created_by              varchar(255)        null,
    created_date            datetime(6)         null,
    last_modified_by        varchar(255)        null,
    last_modified_date      datetime(6)         null,
    active_status           varchar(255)        null,
    aud_idx                 bigint,
    aud_class               varchar(255),
    aud_type                varchar(255),
    aud_message             varchar(255),
    aud_sub_message         varchar(255)
);

ALTER TABLE data_history AUTO_INCREMENT=1;
DROP TABLE data_history;

