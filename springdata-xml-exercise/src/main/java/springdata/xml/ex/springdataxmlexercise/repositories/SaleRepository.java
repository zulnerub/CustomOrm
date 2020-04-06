package springdata.xml.ex.springdataxmlexercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdata.xml.ex.springdataxmlexercise.models.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

}
