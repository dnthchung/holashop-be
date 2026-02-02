package com.chungdt03.holashopbe.services;

import com.chungdt03.holashopbe.dtos.CategoryDTO;
import com.chungdt03.holashopbe.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO category);

    Category getCategoryById(Long categoryId);

    List<Category> getAllCategories();

    Category updateCategory(Long categoryId, CategoryDTO category);

    void deleteCategory(Long categoryId);
}
