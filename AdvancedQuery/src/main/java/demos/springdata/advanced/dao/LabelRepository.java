package demos.springdata.advanced.dao;

import demos.springdata.advanced.entity.Ingredient;
import demos.springdata.advanced.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Label findOneById(long id);
}
