package springdata.xml.demo.xml.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdata.xml.demo.xml.domain.entities.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Phone findByPhoneNumber(String number);
}
