package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;
import softuni.exam.adaptors.LocalDateTimeAdaptor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {
    private BigDecimal price;
    private String description;
    private boolean hasGoldStatus;
    private LocalDateTime addedOn;
    private Car car;
    private Seller seller;
    private Set<Picture> pictures;


    public Offer() {
    }

    public Offer(BigDecimal price, String description, boolean hasGoldStatus, LocalDateTime addedOn) {
        this.price = price;
        this.description = description;
        this.hasGoldStatus = hasGoldStatus;
        this.addedOn = addedOn;
    }

    @ManyToMany
    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Column(name = "price")
    @DecimalMin(value = "0")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "description")
    @Length(min = 5)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "has_gold_status")
    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public void setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    @Column(name = "added_on")
    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }
}
