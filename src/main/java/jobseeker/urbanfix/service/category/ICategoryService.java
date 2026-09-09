package jobseeker.urbanfix.service.category;


import jobseeker.urbanfix.model.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategory();
    Category updateCategory(Category category,Long id);
    void deleteCategory(Long id);

    Category getCategoryByName(String name);
}
