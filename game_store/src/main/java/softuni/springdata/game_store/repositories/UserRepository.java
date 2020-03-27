package softuni.springdata.game_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springdata.game_store.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    User findByEmail(String email);


}
