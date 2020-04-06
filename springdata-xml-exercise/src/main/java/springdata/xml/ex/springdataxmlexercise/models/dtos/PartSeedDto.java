package springdata.xml.ex.springdataxmlexercise.models.dtos;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "part")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartSeedDto {

    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "price")
    private BigDecimal price;
    @XmlAttribute(name = "quantity")
    private int quantity;

    public PartSeedDto() {
    }

    @NotNull(message = "Name can not be null!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DecimalMin(value = "0", message = "Price can not be negative!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
