package alararestaurant.domain.entities;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{
    private String name;
    private int age;
    private Position position;
    private List<Order> orders;

    public Employee() {
    }

    public Employee(String name, int age, Position position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }

    @Column(name = "name", nullable = false)
    @Length(min = 3, max = 31)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "age", nullable = false)
    @Range(min = 15, max = 80)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id", referencedColumnName = "id")
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
