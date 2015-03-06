import dao.Product;

import javax.persistence.*;
import java.util.List;

public class TestJPA {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TestJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TypedQuery<Product> query = entityManager.createQuery("select e from Product e", Product.class);
        List<Product> resultList = query.getResultList();
        resultList.forEach(System.out::println);

        entityManager.close();
    }
}
