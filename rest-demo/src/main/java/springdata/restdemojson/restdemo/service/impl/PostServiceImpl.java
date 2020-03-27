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
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post addPost(Post post) {
        Long authorId;
        if (post.getAuthor() != null && post.getAuthor().getId() != null){
            authorId = post.getAuthor().getId();
        }else{
            authorId = post.getAuthorId();
        }

        if (authorId != null){
            User author = userRepository.findById(authorId)
                    .orElseThrow(() -> new InvalidEntityException("Author with ID= " +
                            authorId + " Does not exist."));
            post.setAuthor(author);
        }
        if (post.getCreated() == null){
            post.setCreated(new Date());
        }
        post.setModified(post.getCreated());

        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return null;
    }

    @Override
    public Post deletePost(Long id) {
        return null;
    }

    @Override
    public Long getPostsCount() {
        return null;
    }
}
