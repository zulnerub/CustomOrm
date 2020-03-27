package softuni.springdata.game_store.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.springdata.game_store.domain.dtos.GameAddDto;
import softuni.springdata.game_store.domain.dtos.GameEditDto;
import softuni.springdata.game_store.domain.entities.Game;
import softuni.springdata.game_store.repositories.GameRepository;
import softuni.springdata.game_store.services.GameService;
import softuni.springdata.game_store.services.UserService;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {
        if (!this.userService.isLoggedUserAdmin()){
            System.out.println("Logged user is not Admin!");
            return;
        }
        Game game = this.modelMapper.map(gameAddDto, Game.class);

        this.gameRepository.saveAndFlush(game);

        System.out.println();
    }

    @Override
    public void editGame(GameEditDto gameEditDto) {
        if (!this.userService.isLoggedUserAdmin()){
            System.out.println("Logged user is not Admin!");
            return;
        }

        Game game = this.modelMapper.map(gameEditDto, Game.class);

    }


}
