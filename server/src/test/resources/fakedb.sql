create database bookdaotest;   
use bookdaotest;
 
 create table Readers (
 id mediumint auto_increment,
 surname char(50) not null,
 name char(50) not null,
 address text,
 passport char(10) not null unique,
 telephone char(14) not null,
 primary key (id)
 );
  
create table Author(
 id mediumint auto_increment,
 name char(50) not null,
 surname char(50) not null,
 patronymic char(50) not null,
 dateofbirth date not null,
 country char(50) not null,
 primary key (id)
 );

create table Publishing(
 id mediumint auto_increment,
 name char(70) not null,
 city char(50) not null,
 telephone char(14) not null,
 email char(50),
 site char(50),
 primary key(id)
 );
 
 create table Books(
 id mediumint auto_increment,
 title char(50) not null,
 author_id mediumint not null,
 publishing_id mediumint not null,
 year int not null,
 pagenumber int not null,
 price float not null,
 primary key (id),
 foreign key(author_id) references author(id),
 foreign key(publishing_id) references publishing(id)
 );
 
 create table Employees(
id mediumint auto_increment,
name char(50) not null,
surname char(50) not null,
patronymic char(50) not null,
dateofbirth date not null,
position char(50) not null,
primary key(id)
);

create table Borrow(
 id mediumint auto_increment,
 books_id mediumint not null,
 employee_id mediumint not  null,
 date_of_borrow date not null,
 date_of_return date not null,
 readers_id mediumint not null,
 primary key (id),
 foreign key(books_id) references books(id),
 foreign key(readers_id) references readers(id),
 foreign key(employee_id) references employees(id)
 );
 
 create table Genre(
 id mediumint auto_increment,
 genre_type char(50) not null,
 primary key(id)
 );

create table Genre_lists(
 book_id mediumint not null,
 genre_id mediumint not null,
 foreign key(book_id) references Books(id),
 foreign key(genre_id) references Genre(id)
);
 
create index readersname on readers(name, surname);
create index authorname on author(name, surname);
create index Employeesname on Employees(name, surname);
create index booktitle on Books (title);



INSERT INTO author (id, name, surname, patronymic, dateofbirth, country)
    VALUES (1, 'author', 's', 'p', current_date(), 'c');
INSERT INTO publishing (id, name, city, telephone, email, site)
    VALUES (1, 'publishing', 'c', 't', 'e', 's');
INSERT INTO genre (id, genre_type)
	  VALUES (1, 'first genre');
INSERT INTO genre (id, genre_type)
	  VALUES (2, 'second genre');