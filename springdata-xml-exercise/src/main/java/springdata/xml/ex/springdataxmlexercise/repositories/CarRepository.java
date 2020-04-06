package springdata.xml.ex.springdataxmlexercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springdata.xml.ex.springdataxmlexercise.models.entities.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);
}
