import models.IdNameYearCategory;
import models.sql.Book;
import models.sql.Category;
import org.javatuples.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RegularQueries implements AutoCloseable {

    private Properties properties = new Properties();
    private Connection connection;

    public RegularQueries(String pathToProperties) throws IOException, SQLException {
        FileReader reader = new FileReader(pathToProperties);
        properties.load(reader);
        connection = DriverManager.getConnection(
            properties.getProperty("url"),
            properties.getProperty("user"),
            properties.getProperty("password"));
        reader.close();
    }

    public RegularQueries() throws IOException, SQLException {
        this("database.properties");
    }

    private ResultSet select(@org.intellij.lang.annotations.Language("SQL") String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public List<Book> getBySurname() throws SQLException {
        ResultSet rs = select("""
            select *
                from books
                where author_surname ilike 'S%';""");
        List<Book> result = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setBookName(rs.getString("book_name"));
            book.setAuthorSurname(rs.getString("author_surname"));
            book.setAuthorName(rs.getString("author_name"));
            book.setYear(rs.getShort("year"));
            book.setPublisher(rs.getString("publisher"));
            book.setCost(rs.getBigDecimal("cost"));
            book.setPrice(rs.getBigDecimal("price"));
            book.setProfit(rs.getBigDecimal("profit"));
            book.setTypeId(rs.getInt("type_id"));
            result.add(book);
        }
        return result;
    }

    public List<IdNameYearCategory> getIdNameYearCategory() throws SQLException {
        ResultSet rs = select( """
            select books.id, book_name, year, cat_name
                from books
                    left join category
                        on books.type_id=category.id;""");
        List<IdNameYearCategory> result = new ArrayList<>();
        while (rs.next()) {
            IdNameYearCategory struct = new IdNameYearCategory();
            struct.setId(rs.getLong("id"));
            struct.setBook_name(rs.getString("book_name"));
            struct.setYear(rs.getShort("year"));
            struct.setCat_name(rs.getString("cat_name"));
            result.add(struct);
        }
        return result;
    }

    public Long countBooksByPrice() throws SQLException {
        ResultSet rs = select( """
            select count(*)
                from books
                where price='20';""");
        Long result = null;
        if (rs.next()) {
            result = rs.getLong(1);
        }
        return result;
    }

    public BigDecimal sumProfitByYear() throws SQLException {
        ResultSet rs = select( """
            select sum(profit)
                from books
                where year > 2017;""");
        BigDecimal result = null;
        if (rs.next()) {
            result = rs.getBigDecimal(1);
        }
        return result;
    }

    public BigDecimal[] minMaxBookPrice() throws SQLException {
        ResultSet rs = select( """
            select min(price) as "Min Price", max(price) as "Max price"
                from books;""");
        BigDecimal[] result = new BigDecimal[2];
        if (rs.next()) {
            result[0] = rs.getBigDecimal(1);
            result[1] = rs.getBigDecimal(2);
        }
        return result;
    }

    public List<Pair<Book, Category>> getFantasyBooks() throws SQLException {
        ResultSet rs = select("""
            select 
                books.id as id1, book_name, author_surname, author_name, year, publisher, cost, price, profit, type_id,
                category.id as id2, cat_name, cat_description
                from books
                    inner join category
                        on books.type_id=category.id
                where category.cat_name='Fantasy';""");
        List<Pair<Book, Category>> result = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getLong("id1"));
            book.setBookName(rs.getString("book_name"));
            book.setAuthorSurname(rs.getString("author_surname"));
            book.setAuthorName(rs.getString("author_name"));
            book.setYear(rs.getShort("year"));
            book.setPublisher(rs.getString("publisher"));
            book.setCost(rs.getBigDecimal("cost"));
            book.setPrice(rs.getBigDecimal("price"));
            book.setProfit(rs.getBigDecimal("profit"));
            book.setTypeId(rs.getInt("type_id"));

            Category category = new Category();
            category.setId(rs.getLong("id2"));
            category.setCatName(rs.getString("cat_name"));
            category.setCatDescription(rs.getString("cat_description"));

            result.add(new Pair<>(book, category));
        }
        return result;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}