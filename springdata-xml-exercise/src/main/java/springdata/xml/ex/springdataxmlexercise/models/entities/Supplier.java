package springdata.xml.ex.springdataxmlexercise.models.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {
    private boolean isImporter;
    private String name;
    private Set<Part> parts;

    public Supplier() {
    }

    public Supplier(boolean isImporter, String name){
        this.isImporter = isImporter;
        this.name = name;
    }

    @Column(name = "is_importer")
    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }
}
