package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import softuni.exam.adaptors.LocalDateAdaptor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class CarSeedDto {

    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private int kilometers;
    @Expose
    private String registeredOn;

    public CarSeedDto() {
    }

    @NotNull
    @Length(min = 2, max = 19)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @NotNull
    @Length(min = 2, max = 19)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
    @XmlJavaTypeAdapter(LocalDateAdaptor.class)
    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }
}
