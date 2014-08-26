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

create table usuario (
  id                        integer not null,
  constraint pk_usuario primary key (id))
;


create table exercicio_usuario (
  exercicio_id                   integer not null,
  usuario_id                     integer not null,
  constraint pk_exercicio_usuario primary key (exercicio_id, usuario_id))
;
create sequence exercicio_seq;

create sequence solucao_do_exercicio_seq;

create sequence usuario_seq;

alter table solucao_do_exercicio add constraint fk_solucao_do_exercicio_exerci_1 foreign key (exercicio_id) references exercicio (id);
create index ix_solucao_do_exercicio_exerci_1 on solucao_do_exercicio (exercicio_id);



alter table exercicio_usuario add constraint fk_exercicio_usuario_exercici_01 foreign key (exercicio_id) references exercicio (id);

alter table exercicio_usuario add constraint fk_exercicio_usuario_usuario_02 foreign key (usuario_id) references usuario (id);

# --- !Downs

drop table if exists exercicio cascade;

drop table if exists exercicio_usuario cascade;

drop table if exists solucao_do_exercicio cascade;

drop table if exists usuario cascade;

drop sequence if exists exercicio_seq;

drop sequence if exists solucao_do_exercicio_seq;

drop sequence if exists usuario_seq;

