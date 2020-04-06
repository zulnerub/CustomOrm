package springdata.xml.ex.springdataxmlexercise.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity{
    private String name;
    private BigDecimal price;
    private int quantity;
    private Supplier supplier;
    private Set<Car> cars;

    public Part() {
    }

    public Part(String name, BigDecimal price, int quantity, Supplier supplierId, Set<Car> cars) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.supplier = supplierId;
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplierId) {
        this.supplier = supplierId;
    }

    @ManyToMany(mappedBy = "parts")
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
