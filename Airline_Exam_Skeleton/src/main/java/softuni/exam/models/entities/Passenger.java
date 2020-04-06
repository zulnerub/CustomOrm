package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "passengers")
public class Passenger extends BaseEntity {
    @Column(name = "first_name")
    @Length(min = 2)
    private String firstName;
    @Column(name = "last_name")
    @Length(min = 2)
    private String lastName;
    @Column(name = "age")
    @Min(value = 0)
    private int age;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email", unique = true, nullable = false)
    @Pattern(regexp = "[a-z0-9]+[a-zA-Z0-9]*@[a-zA-Z0-9]+?(.[a-zA-Z0-9]+)+")
    private String email;
    @ManyToOne
    private Town town;
    @OneToMany(mappedBy = "passenger",cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Passenger() {
    }

    public Passenger(@Length(min = 2) String firstName, @Length(min = 2) String lastName, @Min(value = 0) int age, String phoneNumber, @Pattern(regexp = "[a-z0-9]+[a-zA-Z0-9]*@[a-zA-Z0-9]+?(.[a-zA-Z0-9]+)+") String email, Town town) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.town = town;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
