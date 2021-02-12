alter table app_user add column role varchar(255);
update app_user set role = 'OPERATOR';

insert into app_user (id, username, password, role)
values (3, 'sai_admin', '$2y$12$jmgcjmo.CK5iYXYLfYW0S.Q24ad19h9vCPaKW0iIjZgE4XPunFCzG', 'ADMIN');
