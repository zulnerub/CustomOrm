package softuni.springdata.game_store.services;

import softuni.springdata.game_store.domain.dtos.UserLoginDto;
import softuni.springdata.game_store.domain.dtos.UserRegisterDto;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logout();

    boolean isLoggedUserAdmin();
}
