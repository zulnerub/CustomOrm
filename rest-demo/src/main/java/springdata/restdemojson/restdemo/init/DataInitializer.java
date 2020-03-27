package springdata.restdemojson.restdemo.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdata.restdemojson.restdemo.model.Post;
import springdata.restdemojson.restdemo.model.User;
import springdata.restdemojson.restdemo.service.PostService;
import springdata.restdemojson.restdemo.service.UserService;

import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    private static final List<Post> SAMPLE_POSTS = List.of(
            new Post("Welcome to Spring Data", "asfasfasfasf asfsaf asfasf afas as as as af sa"),
            new Post("Random Second POst", "asfasfasfasf asfsaf asfasf afas as as as af sa"),
            new Post("Third Post Of Something Interesting", "asfasfasfasf asfsaf asfasf afas as as as af sa")
            );

    private static final List<User> SAMPLE_USERS = List.of(
            new User("Default", "Admin", "Admin", "Admin"),
            new User("Ivan", "Petrov", "Ivan","Ivan")
    );


    @Override
    public void run(String... args) throws Exception {
        SAMPLE_USERS.forEach(user -> userService.addUser(user));
        log.info("Created Users: {}", userService.getUsers());

        SAMPLE_POSTS.forEach(post -> {
            post.setAuthor(userService.getUserById(1L));
            postService.addPost(post);
        });
        log.info("Created Posts: {}", postService.getPosts());

    }
}
