create table author
(
    author_id          int auto_increment
        primary key,
    first_name         varchar(128)                             not null,
    last_name          varchar(128)                             not null,
    age                int                                      not null,
    country            varchar(64)                              not null,
    created_date       timestamp    default current_timestamp() null,
    last_modified_date timestamp    default current_timestamp() null,
    last_modified_by   varchar(128) default 'system'            null,
    record_status      int          default 1                   null
);

create table book
(
    book_id            int auto_increment
        primary key,
    author_fk          int                                                       not null,
    name               varchar(254) collate utf8_bin                             not null,
    price              double                                                    not null,
    image              varchar(1024)                                             null,
    genre              varchar(128) collate utf8_bin                             not null,
    amount             int                                                       not null,
    description        varchar(1024) collate utf8_bin                            not null,
    created_date       timestamp                     default current_timestamp() null,
    last_modified_date timestamp                     default current_timestamp() null,
    last_modified_by   varchar(128) collate utf8_bin default 'system'            null,
    record_status      int                           default 1                   null,
    constraint fk_author_book
        foreign key (author_fk) references author (author_id)
            on update cascade on delete cascade
);

create table credit_card
(
    credit_card_id     int auto_increment
        primary key,
    card_number        varchar(12)  default ''                  not null,
    bank               varchar(128)                             not null,
    balance            double                                   not null,
    created_date       timestamp    default current_timestamp() null,
    last_modified_date timestamp    default current_timestamp() null,
    last_modified_by   varchar(128) default 'system'            null,
    record_status      int          default 1                   null,
    constraint card_number
        unique (card_number)
);

create table role
(
    role_id            int auto_increment
        primary key,
    name               varchar(128)                             not null,
    created_date       timestamp    default current_timestamp() null,
    last_modified_date timestamp    default current_timestamp() null,
    last_modified_by   varchar(128) default 'system'            null,
    record_status      int          default 1                   null
);

create table user
(
    user_id            int auto_increment
        primary key,
    credit_card_fk     int                                      null,
    role_fk            int                                      not null,
    username           varchar(254)                             not null,
    password           varchar(254)                             not null,
    created_date       timestamp    default current_timestamp() null,
    last_modified_date timestamp    default current_timestamp() null,
    last_modified_by   varchar(128) default 'system'            null,
    record_status      int          default 1                   null,
    constraint fk_user_credit_card
        foreign key (credit_card_fk) references credit_card (credit_card_id)
            on update cascade,
    constraint fk_user_role
        foreign key (role_fk) references role (role_id)
);

create table member
(
    member_id          int auto_increment
        primary key,
    user_fk            int                                      not null,
    member_number      int                                      not null,
    discount           double                                   not null,
    type               varchar(64)                              not null,
    created_date       timestamp    default current_timestamp() null,
    last_modified_date timestamp    default current_timestamp() null,
    last_modified_by   varchar(128) default 'system'            null,
    record_status      int          default 1                   null,
    constraint fk_member_user
        foreign key (user_fk) references user (user_id)
            on update cascade on delete cascade
);

create table purchase
(
    purchase_id        int auto_increment
        primary key,
    member_fk          int                                      not null,
    book_fk            int                                      not null,
    date               date                                     not null,
    price              double                                   not null,
    created_date       timestamp    default current_timestamp() null,
    last_modified_date timestamp    default current_timestamp() null,
    last_modified_by   varchar(128) default 'system'            null,
    record_status      int          default 1                   null,
    constraint fk_purchase_book
        foreign key (book_fk) references book (book_id)
            on update cascade on delete cascade,
    constraint fk_purchase_member
        foreign key (member_fk) references member (member_id)
            on update cascade on delete cascade
);

