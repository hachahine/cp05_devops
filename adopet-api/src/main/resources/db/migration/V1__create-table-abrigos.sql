-- sql server
create table abrigos(
    id bigint not null identity(1,1),
    nome varchar(100) not null unique,
    telefone varchar(14) not null unique,
    email varchar(100) not null unique,
    primary key(id)
);