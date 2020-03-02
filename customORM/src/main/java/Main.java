import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, SQLException, NoSuchMethodException, InstantiationException, InvocationTargetException {

        String username = "root";
        String password = "root";

        Connector.createConnection(username, password, "orm_db");
        EntityManager<User> entityManager = new EntityManager<>(Connector.getConnection());

        //User user = new User("asdaasdassdas", "simo", 20, new Date(), 5000.00);

        User user = entityManager.findFirst(User.class, " id = 3");
        entityManager.delete(user);

        System.out.println("User is deleted");

        //User user1 = entityManager.findFirst(User.class, " age <= 33");
        //System.out.println(user1.getUsername());

        //List<User> users = (List<User>) entityManager.find(User.class, "age > 32");

        /* for (User userr :
                users) {
            System.out.println(userr.getUsername());
        }

        List<User> allusers = (List<User>) entityManager.find(User.class);

        System.out.println("--------------------------");

        for (User userrr :
                allusers) {
            System.out.println(userrr.getUsername());
        } */

        //entityManager.doCreate(User.class);
    }
}
