package bookshop.system.bookshop.services;




import bookshop.system.bookshop.entities.Category;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;

    Category getCategoryById(Long id);
}
