create sequence clearance_id_seq start 1 increment 50;
create table clearance
(
    id                int4 not null,
    lot_serial_number int4 not null,
    number_of_bags    int4 not null,
    date              date not null,
    primary key (id)
);