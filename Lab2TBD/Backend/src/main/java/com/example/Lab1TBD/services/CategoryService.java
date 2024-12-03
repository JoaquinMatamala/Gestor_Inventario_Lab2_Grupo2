package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.CategoryEntity;
import com.example.Lab1TBD.persistence.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity getCategoryById(Long id) {
        return categoryRepository.findCategoryById(id);
    }

    public CategoryEntity getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public void saveCategory(CategoryEntity category) {
        categoryRepository.saveCategory(category);
    }

    public void updateCategory(CategoryEntity category) {
        categoryRepository.updateCategory(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteCategoryById(id);
    }
}
