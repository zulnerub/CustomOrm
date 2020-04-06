package springdata.xml.ex.springdataxmlexercise.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarViewRootNoIdDto {
    @XmlElement(name = "car")
    private List<CarViewNoIdDto> carViewNoIdDtos;

    public List<CarViewNoIdDto> getCarViewNoIdDtos() {
        return carViewNoIdDtos;
    }

    public void setCarViewNoIdDtos(List<CarViewNoIdDto> carViewNoIdDtos) {
        this.carViewNoIdDtos = carViewNoIdDtos;
    }
}
