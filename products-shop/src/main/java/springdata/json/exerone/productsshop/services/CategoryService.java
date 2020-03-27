package springdata.json.exerone.productsshop.services;

import springdata.json.exerone.productsshop.models.dtos.CategorySeedDto;
import springdata.json.exerone.productsshop.models.entities.Category;

import java.util.List;

public interface CategoryService {
    void seedCategories(CategorySeedDto[] categorySeedDtos);

    List<Category> getRandomCategories();
}
