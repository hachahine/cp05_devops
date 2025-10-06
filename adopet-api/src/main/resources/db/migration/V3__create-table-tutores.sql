-- sql server
create table tutores(
    id bigint not null identity(1,1),
    nome varchar(100) not null,
    telefone varchar(14) not null unique,
    email varchar(100) not null unique,
    primary key(id)
);