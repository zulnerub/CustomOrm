package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class PassengerSeedDto {
    @Expose
    @Column(name = "first_name")
    @Length(min = 2)
    private String firstName;
    @Expose
    @Column(name = "last_name")
    @Length(min = 2)
    private String lastName;
    @Expose
    @Column(name = "age")
    @Min(value = 0)
    private int age;
    @Expose
    @Column(name = "phone_number")
    private String phoneNumber;
    @Expose
    @Column(name = "email", unique = true, nullable = false)
    @Pattern(regexp = "[a-z0-9]+[a-zA-Z0-9]*@[a-zA-Z0-9]+?(.[a-zA-Z0-9]+)+")
    private String email;
    @Expose
    @ManyToOne
    private String town;

    public PassengerSeedDto() {
    }

    public PassengerSeedDto(String firstName, String lastName, int age, String phoneNumber, String email, String town) {
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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
