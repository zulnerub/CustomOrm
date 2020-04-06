package softuni.exam.models.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "from-town")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedFromTownDto {
    @XmlElement(name = "name")
    @Length(min = 2)
    @NotNull
    private String name;

    public TicketSeedFromTownDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
