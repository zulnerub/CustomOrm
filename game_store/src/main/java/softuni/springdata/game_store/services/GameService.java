package softuni.springdata.game_store.services;

import softuni.springdata.game_store.domain.dtos.GameAddDto;
import softuni.springdata.game_store.domain.dtos.GameEditDto;

public interface GameService {
    void addGame(GameAddDto gameAddDto);

    void editGame(GameEditDto gameEditDto);
}
