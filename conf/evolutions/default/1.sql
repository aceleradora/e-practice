# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table solucao_do_exercicio (
  id                        integer not null,
  codigo                    varchar(255),
  constraint pk_solucao_do_exercicio primary key (id))
;

create sequence solucao_do_exercicio_seq;




# --- !Downs

drop table if exists solucao_do_exercicio cascade;

drop sequence if exists solucao_do_exercicio_seq;

