package hiberspring.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    private String firstName;

    private String lastName;

    private String position;

    private EmployeeCard card;

    private Branch branch;

    public Employee() {
    }

    public Employee(String firstName, String lastName, String position, EmployeeCard card, Branch branch) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.card = card;
        this.branch = branch;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(nullable = false)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @OneToOne(optional = false)
    @JoinColumn(name = "card_id", referencedColumnName = "id", unique = true)
    public EmployeeCard getCard() {
        return card;
    }

    public void setCard(EmployeeCard card) {
        this.card = card;
    }

    @ManyToOne(optional = false)
    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
