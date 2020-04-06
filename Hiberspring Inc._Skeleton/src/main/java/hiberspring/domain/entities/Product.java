package hiberspring.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    private int clients;

    private Branch branch;

    public Product() {
    }

    public Product(String name, int clients, Branch branch) {
        this.name = name;
        this.clients = clients;
        this.branch = branch;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    @Min(value = 0)
    public int getClients() {
        return clients;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }

    @ManyToOne(optional = false)
    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
