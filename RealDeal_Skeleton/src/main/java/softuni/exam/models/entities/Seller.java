package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;
import softuni.exam.models.enums.Rating;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private Rating rating;
    private String town;

    public Seller() {
    }

    public Seller(String firstName, String lastName, String email, Rating rating, String town) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.rating = rating;
        this.town = town;
    }

    @Column(name = "first_name")
    @Length(min = 2, max = 19)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    @Length(min = 2, max = 19)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email", unique = true)
    @Pattern(regexp = "[a-zA-Z0-9]+@([a-zA-Z0-9]+[-]?)+[.a-zA-Z0-9]+")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "rating", nullable = false)
    @Enumerated(EnumType.STRING)
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Column(name = "town", nullable = false)
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
