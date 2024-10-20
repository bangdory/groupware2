create table mail
(
    id                    int auto_increment
        primary key,
    create_date           datetime(6)  null,
    message               text         null,
    receiver_mail_address varchar(255) null,
    subject               text         null
);