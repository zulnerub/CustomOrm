package springdata.xml.demo.xml.service.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "phones")
@XmlAccessorType(XmlAccessType.FIELD)
public class PhoneRootDto {

    @XmlElement(name = "phone")
    List<PhoneDto> phones;

    public PhoneRootDto() {
    }

    public List<PhoneDto> getPhoneDtoList() {
        return phones;
    }

    public void setPhones(List<PhoneDto> phones) {
        this.phones = phones;
    }
}
