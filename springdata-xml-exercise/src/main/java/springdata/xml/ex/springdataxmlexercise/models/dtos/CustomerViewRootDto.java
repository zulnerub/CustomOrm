package springdata.xml.ex.springdataxmlexercise.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerViewRootDto {

    @XmlElement(name = "customer")
    List<CustomerViewDto> customers;

    public List<CustomerViewDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerViewDto> customers) {
        this.customers = customers;
    }
}
