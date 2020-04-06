package hiberspring.repository;


import hiberspring.domain.dtos.EmployeeViewDto;
import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByCard_Number(String cardNumber);

    @Query("SELECT e FROM Employee AS e " +
            "where e.branch.products.size > 0 " +
            "order by CONCAT(e.firstName, ' ', e.lastName) ASC, LENGTH(e.position) DESC ")
    List<Employee> findAllByBranch_ProductsSizeGreaterThanOne();
}
