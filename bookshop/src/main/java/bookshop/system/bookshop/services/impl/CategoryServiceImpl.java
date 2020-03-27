package bookshop.system.bookshop.services.impl;


import bookshop.system.bookshop.entities.Category;
import bookshop.system.bookshop.repositories.CategoryRepository;
import bookshop.system.bookshop.services.CategoryService;
import bookshop.system.bookshop.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;

import static bookshop.system.bookshop.constants.GlobalConstants.CATEGORIES_FILE_PATH;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() != 0) {
            return;
        }

        String[] fileContent = this.fileUtil
                .readFileContent(CATEGORIES_FILE_PATH);

        Arrays.stream(fileContent).forEach(r -> {
            Category category = new Category(r);

            this.categoryRepository.saveAndFlush(category);
        });


    }

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.findById(id).get();
    }
}
