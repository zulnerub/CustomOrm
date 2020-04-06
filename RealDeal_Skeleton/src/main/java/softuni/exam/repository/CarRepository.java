package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByMakeAndModelAndKilometers(String make, String model, int kilometers);

    @Query("SELECT c FROM Car As c ORDER BY c.pictures.size DESC, c.make ASC")
    List<Car> findAllOrderByPicturesSizeDescAndMakeAsc();

}
