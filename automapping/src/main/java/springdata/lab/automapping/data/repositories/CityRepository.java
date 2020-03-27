package springdata.lab.automapping.data.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdata.lab.automapping.data.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findByName(String name);
}
