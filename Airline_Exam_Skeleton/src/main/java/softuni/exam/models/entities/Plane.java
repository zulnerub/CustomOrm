package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "planes")
public class Plane extends BaseEntity {
    @Column(name = "register_number", unique = true, nullable = false)
    @Length(min = 5)
    private String registerNumber;
    @Column(name = "capacity", nullable = false)
    @Min(value = 0)
    private int capacity;
    @Column(name = "airline")
    @Length(min = 2)
    private String airline;

    public Plane() {
    }

    public Plane(@Length(min = 5) String registerNumber, @Min(value = 0) int capacity, @Length(min = 2) String airline) {
        this.registerNumber = registerNumber;
        this.capacity = capacity;
        this.airline = airline;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}
