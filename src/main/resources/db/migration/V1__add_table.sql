CREATE TABLE lot (id uuid,
number_of_bags integer,
avg_weight_value double precision ,
avg_weight_unit varchar(10),
tot_weight_value double precision,
tot_weight_unit varchar(10) ,
customer uuid,
type varchar(20),
serial_number serial
 );

CREATE TABLE customer (id uuid,
                       name varchar(100),
                       father_name varchar(100),
                       address varchar(200),
                       phone_number bigint
 );
