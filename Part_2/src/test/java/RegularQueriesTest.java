import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.ResultSet;
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
    public void getBySurnameTest() {
        try {
            ResultSet set = regularQueries.getBySurname();
            assertTrue(set.next());
            assertEquals(set.getString(2), "Witcher");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getIdNameYearCategoryTest() {
        try {
            ResultSet set = regularQueries.getIdNameYearCategory();
            int k = 0;
            while (set.next())
                k++;
            assertEquals(k, 5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void countBooksByPriceTest() {
        try {
            ResultSet set = regularQueries.countBooksByPrice();
            assertTrue(set.next());
            assertEquals(set.getInt(1), 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sumProfitByYearTest() {
        try {
            ResultSet set = regularQueries.sumProfitByYear();
            assertTrue(set.next());
            assertEquals(set.getBigDecimal(1), BigDecimal.valueOf(24000,2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void minMaxBookPriceTest() {
        try {
            ResultSet set = regularQueries.minMaxBookPrice();
            assertTrue(set.next());
            assertEquals(set.getBigDecimal(1), BigDecimal.valueOf(800,2));
            assertEquals(set.getBigDecimal(2), BigDecimal.valueOf(3000,2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getFantasyBooksTest() {
        try {
            ResultSet set = regularQueries.getFantasyBooks();
            int k = 0;
            while (set.next())
                k++;
            assertEquals(k, 3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void clean() {
        try {
            regularQueries.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        flyway.clean();
    }
}