package softuni.springdata.game_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.springdata.game_store.domain.entities.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
