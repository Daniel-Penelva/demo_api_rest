package com.project.demo_api_rest.exception;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(Long id) {
        super("Categoria não encontrado com ID: " + id);
    }
    
}
