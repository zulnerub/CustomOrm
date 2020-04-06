package springdata.xml.ex.springdataxmlexercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdata.xml.ex.springdataxmlexercise.models.entities.Supplier;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);

    List<Supplier> findAllByImporterFalse();
}
