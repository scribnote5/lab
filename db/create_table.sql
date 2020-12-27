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

/* album_attached_file table */
CREATE TABLE album_attached_file
(
    idx                 bigint              auto_increment    primary key,
    created_by          varchar(255)        null,
    created_date        datetime(6)         null,
    file_name           varchar(255)        null,
    saved_file_name     varchar(255)        null,
    album_idx           bigint              null,
    file_size           varchar(255)        null
);

/* alumni_association table */
CREATE TABLE alumni_association
(
    idx                     bigint              auto_increment    primary key,
    created_by              varchar(255)        null,
    created_date            datetime(6)         null,
    last_modified_by        varchar(255)        null,
    last_modified_date      datetime(6)         null,
    active_status           varchar(255)        null,
    content                 longtext            null,
    title                   varchar(255)        null
);

/* category table */
CREATE TABLE category
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    title              varchar(255)     null
);

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

/* email table */
CREATE TABLE email
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    email_address      varchar(255)     null,
    note               longtext         null,
    receiver_type      varchar(255)     null
);

/* introduction table */
CREATE TABLE introduction
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    content            longtext         null,
    title              varchar(255)     null
);

/* introduction_attached_file.sql table */
CREATE TABLE introduction_attached_file
(
    idx               bigint auto_increment    primary key,
    created_by        varchar(255)    null,
    created_date      datetime(6)     null,
    file_name         varchar(255)    null,
    saved_file_name   varchar(255)    null,
    introduction_idx  bigint          null,
    file_size         varchar(255)    null
);

/* learn_more_read.sql table */
CREATE TABLE learn_more
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    title              varchar(255)     null,
    download_file_type varchar(255)     null
);

/* learn_more_attached_file table */
CREATE TABLE learn_more_attached_file
(
    idx               bigint auto_increment    primary key,
    created_by        varchar(255)    null,
    created_date      datetime(6)     null,
    file_name         varchar(255)    null,
    saved_file_name   varchar(255)    null,
    learn_more_idx    bigint          null,
    file_size         varchar(255)    null
);

/* login_history table */
CREATE TABLE login_history
(
    idx                     bigint              auto_increment    primary key,
    created_by              varchar(255)        null,
    created_date            datetime(6)         null,
    last_modified_by        varchar(255)        null,
    last_modified_date      datetime(6)         null,
    active_status           varchar(255)        null,
    aud_idx                 bigint,
    aud_ip                  varchar(255),
    aud_location            varchar(255),
    aud_message             varchar(255),
    aud_sub_message         varchar(255),
    aud_login_result_type   varchar(255)
);

/* notice_board table */
CREATE TABLE notice_board
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    content            longtext         null,
    title              varchar(255)     null,
    views              bigint           null
);

/* notice_board_attached_file.sql table */
CREATE TABLE notice_board_attached_file
(
    idx               bigint auto_increment    primary key,
    created_by        varchar(255)    null,
    created_date      datetime(6)     null,
    file_name         varchar(255)    null,
    saved_file_name   varchar(255)    null,
    notice_board_idx  bigint          null,
    file_size         varchar(255)    null
);

CREATE TABLE notice_board_comment
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    notice_board_idx   long             null,
    content            longtext         null
);

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

/* project_attached_file table */
CREATE TABLE project_attached_file
(
    idx                 bigint              auto_increment    primary key,
    created_by          varchar(255)        null,
    created_date        datetime(6)         null,
    file_name           varchar(255)        null,
    saved_file_name     varchar(255)        null,
    project_idx         bigint              null,
    file_size           varchar(255)        null
);

/* publication table */
CREATE TABLE publication
(
    idx                bigint auto_increment    primary key,
    created_by         varchar(255)     null,
    created_date       datetime(6)      null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime(6)      null,
    active_status      varchar(255)     null,
    title              varchar(255)     null,
    authors            varchar(255)     null,
    publication_type   varchar(255)     null,
    publishing_area    varchar(255)     null,
    published_in       varchar(255)     null,
    impact_factor      varchar(255)     null,
    published_date     datetime(6)      null,
    pages              varchar(255)     null,
    volume             varchar(255)     null,
    number             varchar(255)     null,
    doi                varchar(255)     null,
    uri                varchar(255)     null,
    isbn_issn          varchar(255)     null,
    remark             varchar(255)     null
);

/* publication_attached_file.sql table */
CREATE TABLE publication_attached_file (
  idx               bigint auto_increment    primary key,
  created_by        varchar(255)    null,
  created_date      datetime(6)     null,
  file_name         varchar(255)    null,
  saved_file_name   varchar(255)    null,
  publication_idx  bigint          null,
  file_size         varchar(255)    null
);

/* research_field table */
CREATE TABLE research_field
(
    idx                     bigint              auto_increment    primary key,
    created_by              varchar(255)        null,
    created_date            datetime(6)         null,
    last_modified_by        varchar(255)        null,
    last_modified_date      datetime(6)         null,
    active_status           varchar(255)        null,
    content                 longtext            null,
    title                   varchar(255)        null,
    sub_title               varchar(255)        null
);

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

/* seminar_attached_file table */
CREATE TABLE seminar_attached_file
(
    idx               bigint auto_increment    primary key,
    created_by        varchar(255)    null,
    created_date      datetime(6)     null,
    file_name         varchar(255)    null,
    saved_file_name   varchar(255)    null,
    seminar_idx       bigint          null,
    file_size         varchar(255)    null
);

/* user table */
CREATE TABLE user
(
    idx                bigint auto_increment primary key,
    created_by         varchar(255)     null,
    created_date       datetime         null,
    last_modified_by   varchar(255)     null,
    last_modified_date datetime         null,
    active_status      varchar(255)     null,
    admission_date     date             null,
    authority_type     varchar(255)     null,
    birth_date         date             null,
    contact            varchar(255)     null,
    email              varchar(255)     null,
    private_email      varchar(255)     null,
    english_name       varchar(255)     null,
    gender             varchar(255)     null,
    graduated_date     date             null,
    introduction       longtext         null,
    korean_name        varchar(255)     null,
    username           varchar(255)     null,
    user_status        varchar(255)     null,
    user_type          varchar(255)     null,
    messenger_id       varchar(255)     null,
    password           varchar(255)     null,
    github             varchar(255)     null,
    linked_in          varchar(255)     null,
    external_web_page  varchar(255)     null,
    workplace          varchar(255)     null
);

/* user_attached_file table */
CREATE TABLE user_attached_file (
    idx               bigint auto_increment    primary key,
    created_by        varchar(255)    null,
    created_date      datetime(6)     null,
    file_name         varchar(255)    null,
    saved_file_name   varchar(255)    null,
    user_idx          bigint          null,
    file_size         varchar(255)    null
);

INSERT INTO lab.user (idx, created_by, created_date, last_modified_by, last_modified_date, active_status, admission_date, authority_type, birth_date, contact, email, private_email, english_name, gender, graduated_date, introduction, korean_name, username, user_status, user_type, messenger_id, password, github, linked_in, external_web_page, workplace) VALUES (1, 'root', '2020-11-10 20:06:00', null, '2020-11-10 20:06:00', 'ACTIVE', null, 'ROOT', null, null, null, null, null, null, null, null, null, 'root', null, 'PART_TIME_MS', null, '$2a$10$JxldAGrq26dXXqG9hjiE.uk/oVVoir8dDxu2ycfC9aBnZJqhmyc72', null, null, null, null);
INSERT INTO lab.user (idx, created_by, created_date, last_modified_by, last_modified_date, active_status, admission_date, authority_type, birth_date, contact, email, private_email, english_name, gender, graduated_date, introduction, korean_name, username, user_status, user_type, messenger_id, password, github, linked_in, external_web_page, workplace) VALUES (2, 'manager', '2020-11-10 20:06:00', null, '2020-11-11 23:13:16', 'ACTIVE', null, 'MANAGER', '2020-11-05', '010-1234-1234', 'scribnote5@gmail.com', 'scribnote5@gmail.com', 'sihyeong park', 'MALE', null, '', '박시형', 'manager', 'ATTENDING', 'PART_TIME_MS', 'scribnote5@gmail.com', '$2a$10$Fj1npGj5JVYVxshvlNnW7ee8qDAYnaRlVIhoxmk.eA6kX6MgMlFmO', '', '', '', '');
INSERT INTO lab.user (idx, created_by, created_date, last_modified_by, last_modified_date, active_status, admission_date, authority_type, birth_date, contact, email, private_email, english_name, gender, graduated_date, introduction, korean_name, username, user_status, user_type, messenger_id, password, github, linked_in, external_web_page, workplace) VALUES (3, 'manager2', '2020-11-10 20:06:01', null, '2020-11-11 23:12:50', 'ACTIVE', null, 'MANAGER', '2020-11-04', '010-1234-4564', 'scribnote5@gmail.com', 'scribnote5@gmail.com', 'hyuksoo jang', 'MALE', null, '', '장혁수', 'manager2', 'ATTENDING', 'PART_TIME_MS', 'scribnote5@gmail.com', '$2a$10$noFc/7BYBkrOITXysMkiOOJ5lOB8IOaDWm5tuanSvulNjJ0FRcc4W', '', '', '', '');
INSERT INTO lab.user (idx, created_by, created_date, last_modified_by, last_modified_date, active_status, admission_date, authority_type, birth_date, contact, email, private_email, english_name, gender, graduated_date, introduction, korean_name, username, user_status, user_type, messenger_id, password, github, linked_in, external_web_page, workplace) VALUES (4, 'general', '2020-11-10 20:06:01', null, '2020-11-11 23:12:16', 'ACTIVE', null, 'GENERAL', '2020-11-01', '010-8345-0755', 'scribnote5@gmail.com', 'scribnote5@gmail.com', 'byeongkyo jung', 'MALE', null, '', '정병교', 'general', 'ATTENDING', 'PART_TIME_MS', 'scribnote5@gmail.com', '$2a$10$/pdRrlrpF8WqmiBzs6805OG87SNItNuTI.ccGVtyu6IOUnFxXXKme', '', '', '', '');
INSERT INTO lab.user (idx, created_by, created_date, last_modified_by, last_modified_date, active_status, admission_date, authority_type, birth_date, contact, email, private_email, english_name, gender, graduated_date, introduction, korean_name, username, user_status, user_type, messenger_id, password, github, linked_in, external_web_page, workplace) VALUES (5, 'general2', '2020-11-10 20:06:01', null, '2020-11-11 23:11:33', 'ACTIVE', null, 'GENERAL', '2020-11-09', '010-8345-0755', 'scribnote5@gmail.com', 'scribnote5@gmail.com', 'dysong', 'MALE', null, '', '송대영', 'general2', 'ATTENDING', 'FULL_TIME_PhD', 'scribnote5@gmail.com', '$2a$10$OdFz9/Ips1eIFDdayB2tg.RO2oFpd5/aEuW08z.sFkQVofMWQwVZG', '', '', '', '');
INSERT INTO lab.user (idx, created_by, created_date, last_modified_by, last_modified_date, active_status, admission_date, authority_type, birth_date, contact, email, private_email, english_name, gender, graduated_date, introduction, korean_name, username, user_status, user_type, messenger_id, password, github, linked_in, external_web_page, workplace) VALUES (6, 'non_user', '2020-11-10 20:06:01', null, '2020-11-11 23:10:56', 'ACTIVE', null, 'NON_USER', '2020-11-09', '010-8345-0755', 'scribnote5@gmail.com', 'scribnote5@gmail.com', '123', 'MALE', null, '', '송대영', 'non_user', 'ATTENDING', 'PART_TIME_MS', 'scribnote5@gmail.com', '$2a$10$/kfgTYCK8CMZtBnoAkBStue3JRsPfSeDiO8bqWAqO3M4rusH/IDf.', '', '', '', '');
