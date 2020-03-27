package springdata.restdemojson.restdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.restdemojson.restdemo.dao.PostRepository;
import springdata.restdemojson.restdemo.dao.UserRepository;
import springdata.restdemojson.restdemo.exeption.InvalidEntityException;
import springdata.restdemojson.restdemo.model.Post;
import springdata.restdemojson.restdemo.model.User;
import springdata.restdemojson.restdemo.service.PostService;
import springdata.restdemojson.restdemo.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with ID= "
                        + id + " not found!")
        );
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User with ID= "  +
                                              username + " not found!")
        );
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(Long id) {
        return null;
    }

    @Override
    public Long getUsersCount() {
        return null;
    }
}
