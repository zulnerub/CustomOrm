package springdata.xml.ex.springdataxmlexercise.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartViewRootDto {
    @XmlElement(name = "part")
    private List<PartViewDto> parts;

    public List<PartViewDto> getParts() {
        return parts;
    }

    public void setParts(List<PartViewDto> parts) {
        this.parts = parts;
    }
}
