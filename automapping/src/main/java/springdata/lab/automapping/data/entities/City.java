package springdata.lab.automapping.data.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cities")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class City extends BaseEntity{

    @Column
    private String name;

    @OneToMany(mappedBy = "city")
    private Set<Address> addresses;
}
