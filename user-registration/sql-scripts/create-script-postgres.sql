create table phones (id bigserial not null, user_id uuid, city_code varchar(255), country_code varchar(255), number varchar(255), primary key (id));
create table users (is_active boolean not null, created timestamp(6), last_login timestamp(6), modified timestamp(6), id uuid not null, email varchar(255) unique, name varchar(255), password varchar(255), token varchar(255), primary key (id));
alter table if exists phones add constraint FKmg6d77tgqfen7n1g763nvsqe3 foreign key (user_id) references users;
