package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{
    private String name;
    private LocalDateTime dateAndTime;
    private Car car;

    public Picture() {
    }

    public Picture(String name, LocalDateTime dateAndTime ) {
        this.name = name;
        this.dateAndTime = dateAndTime;

    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Column(name = "picture", unique = true)
    @Length(min = 2, max = 19)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "data_and_time")
    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
