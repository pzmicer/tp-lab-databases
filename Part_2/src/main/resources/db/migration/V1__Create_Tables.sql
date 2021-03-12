create table books (
	id serial
		constraint books_pk
			primary key,
	book_name text,
	author_surname text,
	author_name text,
	year smallint,
	publisher text,
	cost numeric(10, 2),
	price numeric(10, 2),
	profit numeric(10, 2)
);

insert into books (book_name, author_name, author_surname, year, publisher, cost, price, profit) values
    ('Harry Potter and the Philosopher''s Stone', 'Joan', 'Rowling', 1997, 'Bloomsbury', 15, 20, 1000),
    ('Witcher', 'Andrzej', 'Sapkowski', 1986, 'superNOWA', 10, 15, 1500),
    ('The Lord of the Rings', 'John', 'Tolkien', 1954, 'George Allen & Unwin', 25, 30, 1200),
    ('Ready Player One', 'Ernest', 'Cline', 2011, 'Random House', 18, 25, 800),
    ('Collection of problems on the theory of algorithms', 'Sergey', 'Sobol', 2020, 'Minsk BSU', 5, 8, 240);

alter table books
    add type_id int;

create table category (
    id serial
        constraint category_pk
            primary key,
    cat_name text,
    cat_description text
);

insert into category (cat_name, cat_description) values
    ('Fantasy', 'something'),
    ('Studying', 'very'),
    ('Science fiction', 'interesting');

alter table books
    add foreign key(type_id) references category(id);

update books
    set type_id = 1
    where id in (1, 2, 3);

update books
    set type_id = 2
    where id = 4;

update books
    set type_id = 3
    where id = 5;
