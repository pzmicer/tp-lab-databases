import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ORMQueriesTest {

    private static Flyway flyway;
    private static ORMQueries ormQueries;

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
            ormQueries = new ORMQueries();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBySurname() {
        var result = ormQueries.getBySurname();
        assertEquals(result.get(0).getBookName(), "Witcher");
    }

    @Test
    void getIdNameYearCategory() {
        var result = ormQueries.getIdNameYearCategory();
        assertEquals(result.size(), 5);
    }

    @Test
    void countBooksByPrice() {
        var result = ormQueries.countBooksByPrice();
        assertEquals(result, 1);
    }

    @Test
    void sumProfitByYear() {
        var result = ormQueries.sumProfitByYear();
        assertEquals(result, BigDecimal.valueOf(24000,2));
    }

    @Test
    void minMaxBookPrice() {
        var result = ormQueries.minMaxBookPrice();
        assertEquals(result[0], BigDecimal.valueOf(800,2));
        assertEquals(result[1], BigDecimal.valueOf(3000,2));
    }

    @Test
    void getFantasyBooks() {
        var result = ormQueries.getFantasyBooks();
        assertEquals(result.size(), 3);
    }

    @AfterAll
    static void clean() {
        flyway.clean();
    }
}