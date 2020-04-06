package softuni.exam.models.dtos;


import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class PictureSeedDto {
    @Expose
    private String name;
    @Expose
    private String dateAndTime;
    @Expose
    private int car;

    public PictureSeedDto() {
    }

    @NotNull
    @Length(min = 2, max = 19)
    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }
}
