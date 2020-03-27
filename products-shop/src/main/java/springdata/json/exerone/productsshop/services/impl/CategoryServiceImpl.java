package springdata.json.exerone.productsshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdata.json.exerone.productsshop.models.dtos.CategorySeedDto;
import springdata.json.exerone.productsshop.models.entities.Category;
import springdata.json.exerone.productsshop.repositories.CategoryRepository;
import springdata.json.exerone.productsshop.services.CategoryService;
import springdata.json.exerone.productsshop.utils.ValidatorUtil;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        if (this.categoryRepository.count() != 0){
            return;
        }

        Arrays.stream(categorySeedDtos)
                .forEach(dto -> {
                    if (this.validatorUtil.isValid(dto)){
                        Category category = this.modelMapper
                                .map(dto, Category.class);

                        this.categoryRepository.saveAndFlush(category);
                    }else {
                        this.validatorUtil.violations(dto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public List<Category> getRandomCategories() {
        Random random = new Random();
        int randomCounter = random.nextInt(3) + 1;
        List<Category> resultList = new ArrayList<>();

        for (int i = 0; i < randomCounter; i++){
            long randomId = random.nextInt((int) this.categoryRepository.count()) + 1;
            resultList.add(this.categoryRepository.getOne(randomId));
        }

        return resultList;
    }
}
