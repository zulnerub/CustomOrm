package springdata.restdemojson.restdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdata.restdemojson.restdemo.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
