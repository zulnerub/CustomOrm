package demos.hibernate;


import demos.hibernate.model.Student;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class HibernateDemoMain {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();

        try (SessionFactory sessionFactory = cfg.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Student student = new Student("Simeon Atansov");
            try {
                session.save(student);
                session.getTransaction().commit();
            } catch (Exception e) {
                if (session.getTransaction() != null)
                    session.getTransaction().rollback();
                throw e;

            }

            session.beginTransaction();
            session.setHibernateFlushMode(FlushMode.MANUAL);
            Student s1 = session.get(Student.class, 1);
            session.getTransaction().commit();

            System.out.printf("Student: %s\n", s1);

            session.beginTransaction();
           // List<Student> students =
                    session.createQuery("FROM Student", Student.class)
                            .setFirstResult(0)
                            .setMaxResults(2)
                            .getResultStream()
                    .forEach(System.out::println);
            session.getTransaction().commit();

            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = builder.createQuery();
            Root<Student> r = criteriaQuery.from(Student.class);
            criteriaQuery.select(r).where(builder.like(r.get("name"), "S%"));
            List<Student> students = session.createQuery(criteriaQuery).getResultList();
            for (Student s : students){
                System.out.println(s);
            }
            session.getTransaction().commit();
        }
    }


}
