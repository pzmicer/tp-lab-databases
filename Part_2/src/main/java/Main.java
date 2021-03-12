import postgreEntities.BooksEntity;
import postgreEntities.CategoryEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*try(RegularQueries rq = new RegularQueries("database.properties")) {
            rq.printResultSet(rq.sumProfitByYear());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }*/
        /*ORMQueries ormQueries = new ORMQueries();
        //List<BooksEntity> books = ormQueries.listBooks();
        List<CategoryEntity> categories = ormQueries.listCategories();*/
        System.out.println("Done");
        BigDecimal a = BigDecimal.ONE;
        Object b = new Object[]{ 5, 3 };
        System.out.println(b);
    }
}
