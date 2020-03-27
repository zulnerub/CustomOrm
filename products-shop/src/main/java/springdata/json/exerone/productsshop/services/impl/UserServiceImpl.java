package springdata.json.exerone.productsshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.json.exerone.productsshop.models.dtos.UserSeedDto;
import springdata.json.exerone.productsshop.models.entities.User;
import springdata.json.exerone.productsshop.repositories.UserRepository;
import springdata.json.exerone.productsshop.services.UserService;
import springdata.json.exerone.productsshop.utils.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }


    @Override
    public void seedUsers(UserSeedDto[] dtos) {
        if (this.userRepository.count() != 0) {
            return;
        }

        Arrays.stream(dtos)
                .forEach(dto -> {
                    if (this.validatorUtil.isValid(dto)) {
                        User user = this.modelMapper
                                .map(dto, User.class);

                        this.userRepository.saveAndFlush(user);
                    } else {
                        this.validatorUtil
                                .violations(dto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public User getRandomUser() {
        Random random = new Random();

        long randomId = random
                .nextInt((int)this.userRepository.count()) + 1;

        return this.userRepository.getOne(randomId);
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.getOne(id);
    }
}
