import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import postgreEntities.BooksEntity;
import postgreEntities.CategoryEntity;

import java.math.BigDecimal;
import java.util.List;

public class ORMQueries {
    SessionFactory sessionFactory;

    public ORMQueries() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<BooksEntity> listBooks() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from BooksEntity");
        List<BooksEntity> result = query.list();
        tx.commit();
        return result;
    }

    public List<CategoryEntity> listCategories() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from CategoryEntity");
        List<CategoryEntity> result = query.list();
        tx.commit();
        return result;
    }

    public List<BooksEntity> getBySurname() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from BooksEntity b where lower(b.authorSurname) like lower('%S')");
        List<BooksEntity> result = query.list();
        tx.commit();
        return result;
    }

    public List<Object[]> getIdNameYearCategory() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("""
                                            select b.id, b.bookName, b.year, c.catName 
                                                from BooksEntity b
                                                    left join CategoryEntity c
                                                        on b.typeId =c.id""");
        List<Object[]> result = query.list();
        tx.commit();
        return result;
    }

    public int countBooksByPrice() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("""
                                            select count(*)
                                                from BooksEntity
                                                where price='$20.00'""");
        int result = (int) query.getSingleResult();
        tx.commit();
        return result;
    }

    public BigDecimal sumProfitByYear() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("""
                                            select sum(profit)
                                                from BooksEntity
                                                where year > 2017""");
        BigDecimal result = (BigDecimal) query.getSingleResult();
        tx.commit();
        return result;
    }

    public BigDecimal[] minMaxBookPrice() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("""
                                            select min(price), max(price)
                                                from BooksEntity""");
        BigDecimal[] result = (BigDecimal[]) query.getSingleResult();
        tx.commit();
        return result;
    }

    public List<Object[]> getFantasyBooks() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("""
                                            from BooksEntity b
                                                inner join CategoryEntity c
                                                    on b.typeId=c.id
                                            where c.catName='Fantasy'""");
        List<Object[]> result = query.list();
        tx.commit();
        return result;
    }
}

class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure(); //"hibernate.cfg.xml"
        /*ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);*/
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