# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table exercicio (
  id                        integer not null,
  enunciado                 TEXT,
  resolvido                 boolean DEFAULT false,
  constraint pk_exercicio primary key (id))
;

create table solucao_do_exercicio (
  id                        integer not null,
  solucao_do_usuario        TEXT,
  id_do_usuario             Integer,
  exercicio_id              integer,
  constraint pk_solucao_do_exercicio primary key (id))
;

create sequence exercicio_seq;

create sequence solucao_do_exercicio_seq;

alter table solucao_do_exercicio add constraint fk_solucao_do_exercicio_exerci_1 foreign key (exercicio_id) references exercicio (id);
create index ix_solucao_do_exercicio_exerci_1 on solucao_do_exercicio (exercicio_id);



# --- !Downs

drop table if exists exercicio cascade;

drop table if exists solucao_do_exercicio cascade;

drop sequence if exists exercicio_seq;

drop sequence if exists solucao_do_exercicio_seq;

