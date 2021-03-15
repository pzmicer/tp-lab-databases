import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class RegularQueriesTest {

    private static Flyway flyway;
    private static RegularQueries regularQueries;

    @BeforeAll
    static void initialize() {
        try {
            Properties properties = new Properties();
            FileReader reader = new FileReader("database.properties");
            properties.load(reader);
            flyway = Flyway.configure().dataSource(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password")).load();
            flyway.migrate();
            regularQueries = new RegularQueries();
        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

    @Test
    void getBySurname() throws SQLException {
        var result = regularQueries.getBySurname();
        assertEquals(result.get(0).getBookName(), "Witcher");
    }

    @Test
    void getIdNameYearCategory() throws SQLException {
        var result = regularQueries.getIdNameYearCategory();
        assertEquals(result.size(), 5);
    }

    @Test
    void countBooksByPrice() throws SQLException {
        var result = regularQueries.countBooksByPrice();
        assertEquals(result, 1);
    }

    @Test
    void sumProfitByYear() throws SQLException {
        var result = regularQueries.sumProfitByYear();
        assertEquals(result, BigDecimal.valueOf(24000,2));
    }

    @Test
    void minMaxBookPrice() throws SQLException {
        var result = regularQueries.minMaxBookPrice();
        assertEquals(result[0], BigDecimal.valueOf(800,2));
        assertEquals(result[1], BigDecimal.valueOf(3000,2));
    }

    @Test
    void getFantasyBooks() throws SQLException {
        var result = regularQueries.getFantasyBooks();
        assertEquals(result.size(), 3);
    }

    @AfterAll
    static void clean() throws SQLException {
        regularQueries.close();
        flyway.clean();
    }
}