import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class RegularQueries implements AutoCloseable {

    private Properties properties = new Properties();
    private Connection connection;
    private String[] queriesSQL = {
            """
        select *
            from books
            where author_surname ilike 'S%';""",
            """
        select books.id, book_name, year, cat_name
            from books
                left join category
                    on books.type_id=category.id;""",
            """
        select count(*)
            from books
            where price='20';""",
            """
        select sum(profit)
            from books
            where year > 2017;""",
            """
        select min(price) as "Min Price", max(price) as "Max price"
            from books;""",
            """
        select *
            from books
                inner join category
                    on books.type_id=category.id
            where category.cat_name='Fantasy';"""
    };

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

    private ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public void printResultSet(ResultSet result) throws SQLException {
            ResultSetMetaData rsmd = result.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (result.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1)
                        System.out.print(",  ");
                    System.out.print(result.getString(i) + " " + rsmd.getColumnName(i));
                }
                System.out.println();
            }
            System.out.println();
    }

    public ResultSet getBySurname() throws SQLException {
        return executeQuery(queriesSQL[0]);
    }

    public ResultSet getIdNameYearCategory() throws SQLException {
        return executeQuery(queriesSQL[1]);
    }

    public ResultSet countBooksByPrice() throws SQLException {
        return executeQuery(queriesSQL[2]);
    }

    public ResultSet sumProfitByYear() throws SQLException {
        return executeQuery(queriesSQL[3]);
    }

    public ResultSet minMaxBookPrice() throws SQLException {
        return executeQuery(queriesSQL[4]);
    }

    public ResultSet getFantasyBooks() throws SQLException {
        return executeQuery(queriesSQL[5]);
    }

    public String[] getQueries() throws SQLException {
        return queriesSQL;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
