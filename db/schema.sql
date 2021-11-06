CREATE TABLE if not exists accident_type (
    id serial primary key,
    name varchar(2000) not null unique
);

CREATE TABLE if not exists accident (
    id serial primary key,
    name varchar(2000) not null,
    text text not null,
    address varchar(2000) not null,
    accident_type_id int references accident_type(id)
);

CREATE TABLE if not exists rules (
    id serial primary key,
    name varchar(2000) not null unique
);

create table if not exists accidents_rules(
    id serial primary key,
    accident_id int not null references accident(id),
    rule_id int not null references rules(id)
);
