import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import models.hibernate.BooksEntity;
import models.hibernate.CategoryEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ORMQueries {

    SessionFactory sessionFactory;

    public ORMQueries() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<BooksEntity> listBooks() {
        List<BooksEntity> result = select("from BooksEntity");
        return result;
    }

    public List<CategoryEntity> listCategories() {
        List<CategoryEntity> result = select("from CategoryEntity");
        return result;
    }

    private List select(@org.intellij.lang.annotations.Language("HQL") String queryString) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tr = session.beginTransaction();
        Query query = session.createQuery(queryString);
        List result = query.getResultList();
        tr.commit();
        return result;
    }

    private Object selectSingle(@org.intellij.lang.annotations.Language("HQL") String queryString) {
        return select(queryString).get(0);
    }

    public List<BooksEntity> getBySurname() {
        List<BooksEntity> result = select("from BooksEntity b where lower(b.authorSurname) like 's%'");
        return result;
    }

    public List<Object[]> getIdNameYearCategory() {
        List<Object[]> result = select("""
            select b.id, b.bookName, b.year, c.catName 
                from BooksEntity b
                    left join CategoryEntity c
                        on b.typeId =c.id""");
        return result;
    }

    public Long countBooksByPrice() {
        Long result = (Long) selectSingle("""
            select count(*)
                from BooksEntity
                where price=20.00""");
        return result;
    }

    public BigDecimal sumProfitByYear() {
        BigDecimal result = (BigDecimal) selectSingle("""
            select sum(profit)
                from BooksEntity
                where year > 2017""");
        return result;
    }

    public BigDecimal[] minMaxBookPrice() {
        Object[] result = (Object[]) selectSingle("""
            select min(price), max(price)
                from BooksEntity""");
        return Arrays.stream(result).toArray(BigDecimal[]::new);
    }

    public List<Object[]> getFantasyBooks() {
        List<Object[]> result = select("""
            from BooksEntity b
                inner join CategoryEntity c
                    on b.typeId=c.id
            where c.catName='Fantasy'""");
        return result;
    }
}

class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure(); //"hibernate.cfg.xml"
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().configure();
        sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
}