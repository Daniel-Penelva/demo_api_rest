package com.project.demo_api_rest.service;

import java.util.List;

import com.project.demo_api_rest.model.Category;

public interface CategoryService {

    Category addCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);

}
