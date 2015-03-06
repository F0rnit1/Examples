import dao.Product;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class TestHibernate {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        List<Product> list = session.createQuery("from Product").list();
        list.forEach(System.out::println);

        session.close();
        sessionFactory.close();
    }
}
