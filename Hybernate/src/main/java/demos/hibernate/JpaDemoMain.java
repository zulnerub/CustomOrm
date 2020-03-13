package demos.hibernate;

import demos.hibernate.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaDemoMain {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();
        User user = new User("John Johnson");
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        em.persist(user);
        User u = em.find(User.class, 1);
        System.out.printf("User: %s\n", u);

        em.getTransaction().begin();
        // List<User> users =
        em.createQuery("SELECT u FROM User u", User.class)
                .setFirstResult(1)
                .setMaxResults(5)
                .getResultStream()
                .forEach(System.out::println);
        em.getTransaction().commit();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = builder.createQuery();
        Root<User> r = criteriaQuery.from(User.class);
        criteriaQuery.select(r).where(builder.like(r.get("name"), "J%"));
        em.createQuery(criteriaQuery).getResultStream().forEach(System.out::println);


    }
}
