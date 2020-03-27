package softuni.springdata.game_store.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Game extends BaseEntity {
    private String description;
    private String image;
    private BigDecimal price;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    private Double size;
    private String title;
    private String trailer;

    //@ManyToMany(mappedBy = "games")
   // private Set<User> users;

  //  @ManyToMany(mappedBy = "orderedGames")
    //private Set<Order> orders;
}
