package com.project.demo_api_rest.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.demo_api_rest.exception.CategoryNotFoundException;
import com.project.demo_api_rest.model.Category;
import com.project.demo_api_rest.repository.CategoryRepository;
import com.project.demo_api_rest.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return findByIdOrThrow(id);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category categoryBD = findByIdOrThrow(id);
        categoryBD.setName(category.getName());
        return categoryRepository.save(categoryBD);
    }

    @Override
    public void deleteCategory(Long id) {
        findByIdOrThrow(id);
        categoryRepository.deleteById(id);
    }

    // Método que busca o id para verificar se a categoria existe ou não existe.
    private Category findByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

}
