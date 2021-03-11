import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ORMQueriesTest {

    ORMQueries ormQueries;

    ORMQueriesTest() {
        ormQueries = new ORMQueries();
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
}