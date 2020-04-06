package springdata.xml.ex.springdataxmlexercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springdata.xml.ex.springdataxmlexercise.models.entities.Customer;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByNameAndBirthDate(String name, LocalDateTime localDateTime);

    @Query("SELECT c FROM Customer AS c " +
            "ORDER BY c.birthDate, c.youngDriver")
    List<Customer> findAllByBirthDateAndIsYoungDriver();

    @Query(value = "SELECT name, c.sales.size, c.sales FROM customers AS c ", nativeQuery = true)
    List<Customer> findAllBySales
}
