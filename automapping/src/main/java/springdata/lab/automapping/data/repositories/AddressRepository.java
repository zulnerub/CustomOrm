package springdata.lab.automapping.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdata.lab.automapping.data.entities.Address;
import springdata.lab.automapping.data.entities.City;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByCity(City city);



}
