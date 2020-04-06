package alararestaurant.domain.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity{

    private String name;
    private Set<Employee> employees;

    public Position() {
    }

    public Position(String name) {
        this.name = name;
    }

    @Column(name = "name", unique = true, nullable = false)
    @Length(min = 3, max = 31)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "position")
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
