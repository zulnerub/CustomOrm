package alararestaurant.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderItemSeedRootDto {
    @XmlElement(name = "item")
    private List<OrderItemSeedDto> orderItems;

    public OrderItemSeedRootDto() {
    }

    public List<OrderItemSeedDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemSeedDto> orderItems) {
        this.orderItems = orderItems;
    }
}
