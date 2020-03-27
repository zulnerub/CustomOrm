package softuni.springdata.game_store.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order extends BaseEntity {

    @ManyToOne
    private User buyer;

    @ManyToMany
    @JoinTable(name = "orders_ordered_games",
                joinColumns = @JoinColumn(name = "order_id"),
                inverseJoinColumns = @JoinColumn(name = "ordered_game_id"))
    private Set<Game> orderedGames;
}
