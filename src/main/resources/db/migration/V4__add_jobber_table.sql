create sequence jobber_id_seq start 1 increment 50;
create table jobber
(
    id              int4 not null,
    name            varchar(255) not null,
    address         varchar(255) not null,
    phone_number    varchar(255),
    primary key (id)
);
