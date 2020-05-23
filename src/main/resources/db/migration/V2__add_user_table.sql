create sequence app_user_id_seq start 1 increment 50;
create table app_user
(
    id       int4 not null,
    username varchar(255),
    password varchar(255),
    primary key (id),
    unique (username)
);

-- insert into app_user (id, username, password)
-- values (2, 'username_t', '$2y$12$jmgcjmo.CK5iYXYLfYW0S.Q24ad19h9vCPaKW0iIjZgE4XPunFCzG')