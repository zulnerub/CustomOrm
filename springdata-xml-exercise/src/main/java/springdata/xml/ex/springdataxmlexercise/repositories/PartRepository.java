package springdata.xml.ex.springdataxmlexercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import springdata.xml.ex.springdataxmlexercise.models.entities.Part;

public interface PartRepository extends JpaRepository<Part, Long> {
    Part findByName(String name);
}
