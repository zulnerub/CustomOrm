package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {
    @Column(name = "serial_number", unique = true, nullable = false)
    @Length(min = 2)
    private String serialNumber;
    @Column(name = "price")
    @DecimalMin(value = "0.01")
    private BigDecimal price;
    @Column(name = "takeoff")
    private LocalDateTime takeoff;
    @ManyToOne
    private Town fromTown;
    @ManyToOne
    private Town toTown;
    @ManyToOne
    private Plane plane;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger;

    public Ticket() {
    }

    public Ticket(@Length(min = 2) String serialNumber, @DecimalMin(value = "0.01") BigDecimal price, LocalDateTime takeoff, Town fromTown, Town toTown, Plane plane, Passenger passenger) {
        this.serialNumber = serialNumber;
        this.price = price;
        this.takeoff = takeoff;
        this.fromTown = fromTown;
        this.toTown = toTown;
        this.plane = plane;
        this.passenger = passenger;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(LocalDateTime takeoff) {
        this.takeoff = takeoff;
    }

    public Town getFromTown() {
        return fromTown;
    }

    public void setFromTown(Town fromTown) {
        this.fromTown = fromTown;
    }

    public Town getToTown() {
        return toTown;
    }

    public void setToTown(Town toTown) {
        this.toTown = toTown;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
