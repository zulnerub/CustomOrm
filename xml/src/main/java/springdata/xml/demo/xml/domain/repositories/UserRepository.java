package springdata.xml.demo.xml.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdata.xml.demo.xml.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
