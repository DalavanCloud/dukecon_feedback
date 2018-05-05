create table feedback (
  id bigint,
  author varchar(255) not null,
  conference varchar(255) not null,
  talk varchar(255),
  comment varchar(4096),
  rating int,
  primary key (id)
  );
create sequence if not exists hibernate_sequence;
