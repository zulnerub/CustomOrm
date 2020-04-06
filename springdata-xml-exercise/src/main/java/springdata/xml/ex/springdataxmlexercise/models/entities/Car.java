package springdata.xml.ex.springdataxmlexercise.models.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    private String make;
    private String model;
    private Long travelledDistance;
    private Set<Part> parts;

    public Car() {
    }

    public Car(String make, String model, Long travelledDistance) {
        this.make = make;
        this.model = model;
        this.travelledDistance = travelledDistance;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cars_parts",
        joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "part_id", referencedColumnName = "id"))
    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}
