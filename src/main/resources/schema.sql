DROP TABLE IF EXISTS trainee_trainer;
DROP TABLE IF EXISTS trainings;
DROP TABLE IF EXISTS trainer;
DROP TABLE IF EXISTS trainee;
DROP TABLE IF EXISTS token;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS training_types;

create table users
(
    user_id bigint auto_increment primary key,
    first_name varchar(255) not null,
    is_active  boolean      not null,
    last_name  varchar(255) not null,
    password   varchar(255) not null,
    username   varchar(255) not null
);


create table training_types
(
    id bigint not null primary key,
    training_type_name enum ('BOXING', 'JIUJITSU', 'KARATE') not null
);

create table trainee
(
    address       varchar(255) null,
    date_of_birth date         null,
    user_id       bigint primary key references users(user_id) on delete cascade
);

create table trainer
(
    user_id       bigint primary key references users(user_id) on delete cascade,
    specialization_id bigint
);

create table trainings (
   training_id      bigint auto_increment primary key,
   duration         decimal(21) not null,
   training_date    date not null,
   training_name    varchar(255) not null,
   trainee_id       bigint,
   trainer_id       bigint,
   training_type_id bigint
);

create table trainee_trainer
(
    trainer_id bigint not null,
    trainee_id bigint not null,
    primary key (trainer_id, trainee_id),
    foreign key (trainee_id) references trainee(user_id) on delete cascade,
    foreign key (trainer_id) references trainer(user_id) on delete cascade
);

create table token (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255),
    inactive BOOLEAN,
    user_id bigint,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
