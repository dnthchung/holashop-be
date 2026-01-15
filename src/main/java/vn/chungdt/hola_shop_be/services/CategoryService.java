package vn.chungdt.hola_shop_be.services;

import java.util.List;

import vn.chungdt.hola_shop_be.dtos.CategoryDTO;
import vn.chungdt.hola_shop_be.models.Category;

public interface CategoryService {
    /**
     * 1. get all categories
     * 2. get category by id
     * 3. create category
     * 4. update category
     * 5. delete category
     */

    List<Category> getAllCategories();
    Category getCategoryById(Long categoryId);
    Category createCategory(CategoryDTO category);
    Category updateCategory(Long categoryId, CategoryDTO category);
    void deleteCategory(Long categoryId);
}
