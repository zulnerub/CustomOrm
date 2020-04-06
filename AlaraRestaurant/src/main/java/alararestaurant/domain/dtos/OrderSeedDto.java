package alararestaurant.domain.dtos;

import alararestaurant.adaptors.LocalDateTimeAdaptor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.List;


@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderSeedDto {
    @XmlElement(name = "customer")
    private String customer;

    @XmlElement(name = "employee")
    private String employee;

    @XmlElement(name = "date-time")
    @XmlJavaTypeAdapter(LocalDateTimeAdaptor.class)
    private LocalDateTime dateTime;

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "items")
    private OrderItemSeedRootDto orderItems;

    public OrderSeedDto() {
    }

    @NotNull
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @NotNull
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @NotNull
    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @Enumerated(EnumType.STRING)
    @NotNull
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OrderItemSeedRootDto getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItemSeedRootDto orderItems) {
        this.orderItems = orderItems;
    }
}
