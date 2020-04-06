package alararestaurant.domain.entities;


import alararestaurant.domain.enums.OrderType;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    private String customer;
    private LocalDateTime dateTime;
    private OrderType type = OrderType.ForHere;
    private Employee employee;
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(String customer, LocalDateTime localDateTime, OrderType type, Employee employee) {
        this.customer = customer;
        this.dateTime = localDateTime;
        this.type = type;
        this.employee = employee;
    }

    @Column(nullable = false)
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(name = "date_time", nullable = false)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime localDateTime) {
        this.dateTime = localDateTime;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
