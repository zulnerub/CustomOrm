package springdata.json.exerone.productsshop.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private Integer age;
    private Set<User> friends;
    private Collection<Product> productsSold;
    private Collection<Product> productsBought;

    public User() {
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    @Length(min = 3, message = "Last name too short")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "users_friends",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @OneToMany(mappedBy = "seller", fetch = EAGER)
    public Collection<Product> getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(Collection<Product> productsSold) {
        this.productsSold = productsSold;
    }

    @OneToMany(mappedBy = "buyer")
    public Collection<Product> getProductsBought() {
        return productsBought;
    }

    public void setProductsBought(Collection<Product> productsBought) {
        this.productsBought = productsBought;
    }
}
