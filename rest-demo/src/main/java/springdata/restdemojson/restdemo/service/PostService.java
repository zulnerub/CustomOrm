package springdata.restdemojson.restdemo.service;

import springdata.restdemojson.restdemo.model.Post;

import java.util.Collection;

public interface PostService {
    Collection<Post> getPosts();
    Post addPost(Post post);
    Post updatePost(Post post);
    Post deletePost(Long id);
    Long getPostsCount();
}
