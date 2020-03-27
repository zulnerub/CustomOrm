package springdata.json.exerone.productsshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdata.json.exerone.productsshop.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
