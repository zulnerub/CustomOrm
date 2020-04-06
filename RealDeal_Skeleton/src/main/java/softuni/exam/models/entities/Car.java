package softuni.exam.models.entities;


import org.hibernate.validator.constraints.Length;
import softuni.exam.adaptors.LocalDateAdaptor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "cars")
public class Car extends BaseEntity{
    private String make;
    private String model;
    private int kilometers;
    private LocalDate registeredOn;
    private Set<Picture> pictures;

    public Car() {
    }

    public Car(String make, String model, int kilometers, LocalDate registeredOn) {
        this.make = make;
        this.model = model;
        this.kilometers = kilometers;
        this.registeredOn = registeredOn;

    }

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    @NotNull
    @Min(value = 0)
    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    @NotNull
    @Column(name = "make")
    @Length(min = 2, max = 19)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @NotNull
    @Column(name = "model")
    @Length(min = 2, max = 19)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "registered_on")
    @XmlJavaTypeAdapter(LocalDateAdaptor.class)
    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }
}
