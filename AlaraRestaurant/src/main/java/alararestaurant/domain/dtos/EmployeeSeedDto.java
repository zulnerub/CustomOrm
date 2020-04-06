package alararestaurant.domain.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class EmployeeSeedDto {
    @Expose
    private String name;

    @Expose
    private int age;

    @Expose
    private String position;

    public EmployeeSeedDto() {
    }

    @NotNull
    @Length(min = 3, max = 31)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Range(min = 15, max = 80)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NotNull
    @Length(min = 3, max = 30)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
