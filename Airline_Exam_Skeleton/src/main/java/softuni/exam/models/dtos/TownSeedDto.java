package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

public class TownSeedDto {
    @Expose
    @Length(min = 2)
    private String name;
    @Expose
    @Min(value = 1)
    private long population;
    @Expose
    private String guide;

    public TownSeedDto() {
    }

    public TownSeedDto(String name, long population, String guide) {
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
