package softuni.springdata.game_store.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.springdata.game_store.domain.dtos.UserDto;
import softuni.springdata.game_store.domain.dtos.UserLoginDto;
import softuni.springdata.game_store.domain.dtos.UserRegisterDto;
import softuni.springdata.game_store.domain.entities.Role;
import softuni.springdata.game_store.domain.entities.User;
import softuni.springdata.game_store.repositories.UserRepository;
import softuni.springdata.game_store.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private UserDto userDto;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        User user = this.modelMapper.map(userRegisterDto, User.class);

        user.setRole(this.userRepository.count() == 0 ? Role.ADMIN : Role.USER);

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        User user = this.userRepository
                .findByEmail(userLoginDto.getEmail());

        if (user == null) {
            System.out.println("Incorrect username / password !");
        } else {
            this.userDto = this.modelMapper.map(user, UserDto.class);
            System.out.printf("Successfully logged user %s%n",
                    user.getFullName());
        }
    }

    @Override
    public void logout() {
        if (this.userDto == null){
            System.out.println("Cannot log out. No user was logged in.");
        }else{
            String name = this.userDto.getFullName();
            this.userDto = null;
            System.out.printf("User %s successfully logout.", name);
        }

    }

    @Override
    public boolean isLoggedUserAdmin() {
        return this.userDto.getRole().equals(Role.ADMIN);
    }
}
