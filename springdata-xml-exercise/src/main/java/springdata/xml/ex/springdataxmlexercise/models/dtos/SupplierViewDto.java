package springdata.xml.ex.springdataxmlexercise.models.dtos;


import javax.xml.bind.annotation.*;

@XmlRootElement (name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierViewDto {
    @XmlAttribute(name = "id")
    private Long id;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "parts-count")
    private int partsCount;

    public SupplierViewDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }
}
