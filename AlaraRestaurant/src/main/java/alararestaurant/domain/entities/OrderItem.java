package alararestaurant.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {
    private Order order;
    private Item item;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne(targetEntity = Item.class)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Column(nullable = false)
    @Min(value = 1)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
