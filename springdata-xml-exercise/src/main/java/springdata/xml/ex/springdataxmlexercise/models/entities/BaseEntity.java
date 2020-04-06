package springdata.xml.ex.springdataxmlexercise.models.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static javax.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public abstract class BaseEntity {
    private long id;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
