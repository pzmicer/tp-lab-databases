select *
    from books
    where author_surname ilike 'S%';

select books.id, book_name, year, cat_name
    from books
        left join category
            on books.type_id=category.id;

select count(*)
    from books
    where price='20';

select sum(profit)
    from books
    where year > 2017;

select min(price) as "Min Price", max(price) as "Max price"
    from books;

select *
    from books
        inner join category
            on books.type_id=category.id
    where category.cat_name='Fantasy';