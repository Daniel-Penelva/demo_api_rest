package com.project.demo_api_rest.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long id) {
        super("Produto não encontrado com ID: " + id);
    }
    
}
