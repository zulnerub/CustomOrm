package springdata.json.exerone.productsshop.services;

import springdata.json.exerone.productsshop.models.dtos.UserSeedDto;
import springdata.json.exerone.productsshop.models.entities.User;

public interface UserService {
    void seedUsers(UserSeedDto[] dtos);

    User getRandomUser();

    User getUserById(Long id);

}
