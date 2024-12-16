package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.CategoryEntity;

import java.util.List;

public interface CategoryRepository {
    List<CategoryEntity> findAllCategories();
    CategoryEntity findCategoryById(Long category_id);
    void saveCategory(CategoryEntity category);
    void updateCategory(CategoryEntity category);
    void deleteCategoryById(Long category_id);

    CategoryEntity findCategoryByName(String category_name);
}
