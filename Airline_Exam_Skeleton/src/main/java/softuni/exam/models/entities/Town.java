package softuni.exam.models.entities;


import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {
    @Column(name = "name", unique = true, nullable = false)
    @Length(min = 2)
    private String name;
    @Column(name = "population", nullable = false)
    @Min(value = 1)
    private long population;
    @Column(name = "guide")
    private String guide;

    public Town() {
    }

    public Town(@Length(min = 2) String name, @Min(value = 0) long population, String guide) {
        this.name = name;
        this.population = population;
        this.guide = guide;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }
}
