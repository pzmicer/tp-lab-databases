create table books
(
	id serial
		constraint books_pk
			primary key,
	book_name text,
	author_surname text,
	author_name text,
	year smallint,
	publisher text,
	cost money,
	price money,
	profit money
);

insert into books (book_name, author_name, author_surname, year, publisher, cost, price, profit)
    values ('Harry Potter and the Philosopher''s Stone', 'Joan', 'Rowling', 1997, 'Bloomsbury', 15, 20, 1000);

insert into books (book_name, author_name, author_surname, year, publisher, cost, price, profit)
    values ('Witcher', 'Andrzej', 'Sapkowski', 1986, 'superNOWA', 10, 15, 1500);

insert into books (book_name, author_name, author_surname, year, publisher, cost, price, profit)
    values ('The Lord of the Rings', 'John', 'Tolkien', 1954, 'George Allen & Unwin', 25, 30, 1200);

insert into books (book_name, author_name, author_surname, year, publisher, cost, price, profit)
    values ('Ready Player One', 'Ernest', 'Cline', 2011, 'Random House', 18, 25, 800);

insert into books (book_name, author_name, author_surname, year, publisher, cost, price, profit)
    values ('Collection of problems on the theory of algorithms', 'Sergey', 'Sobol', 2020, 'Minsk BSU', 5, 8, 240);

alter table books
    add type_id int;

create table category
(
    id serial
        constraint category_pk
            primary key,
    cat_name text,
    cat_description text
);

insert into category (cat_name, cat_description)
    values ('Fantasy', '');

insert into category (cat_name, cat_description)
    values ('Studying', '');

insert into category (cat_name, cat_description)
    values ('Science fiction', '');

update books
    set type_id = 1
    where id in (1, 2, 3);

update books
    set type_id = 2
    where id = 4;

update books
    set type_id = 3
    where id = 5;
