package softuni.exam.models.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarOfferSeedDto {
    @XmlElement(name = "id")
    private long id;

    public CarOfferSeedDto() {
    }

    @NotNull
    @Min(value = 1)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
