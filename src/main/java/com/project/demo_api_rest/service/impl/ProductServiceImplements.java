package com.project.demo_api_rest.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.demo_api_rest.enums.ProductState;
import com.project.demo_api_rest.exception.CategoryNotFoundException;
import com.project.demo_api_rest.exception.ProductNotFoundException;
import com.project.demo_api_rest.model.Category;
import com.project.demo_api_rest.model.Product;
import com.project.demo_api_rest.repository.CategoryRepository;
import com.project.demo_api_rest.repository.ProductRepository;
import com.project.demo_api_rest.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImplements implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(Product product) {
        System.out.println("Produto sendo salvo na service: " + product); // Adicione este log
        return productRepository.save(product);
    }

    @Override
    public Product associateCategoryInProduct(Long categoryId, Product product) {
        findByIdCategory(categoryId);
        return addProduct(product);
    }

    /*  Ou pode Fazer assim no método associateCategoryInProduct: 
        
        @Override
        public Product associateCategoryInProduct1(Long categoryId, Product product) {
            Category saveCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

            product.setCategory(saveCategory);
            return productRepository.save(product);
        }
    */

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findByNameProduct(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Produto com o nome " + name + " não encontrado"));
    }

    @Override
    public Product findByIdProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product productBD = findByIdOrThrow(id);

        productBD.setName(product.getName());
        productBD.setDescription(product.getDescription());
        productBD.setPrice(product.getPrice());
        productBD.setQuantity(product.getQuantity());
        productBD.setProductState(product.getProductState());
        productBD.setCategory(product.getCategory());  // Bastou essa linha para atualizar a categoria

        return productRepository.save(productBD);
    }

    @Override
    public Product updatePriceProduct(Long id, BigDecimal price) {
        Product productBD = findByIdOrThrow(id);

        productBD.setPrice(price);
        return productRepository.save(productBD);
    }

    @Override
    public Product updatePriceAndQuantityProduct(Long id, BigDecimal price, int quantity) {
        Product productBD = findByIdOrThrow(id);

        productBD.setPrice(price);
        productBD.setQuantity(quantity);
        return productRepository.save(productBD);
    }

    @Override
    public void deleteProduct(Long id) {
        findByIdOrThrow(id);
        productRepository.deleteById(id);

    }

    @Override
    public Product modifyProductState(Long id, ProductState productState) {
        Product productBD = findByIdOrThrow(id);

        productBD.setProductState(productState);
        return productRepository.save(productBD);
    }

    @Override
    public List<Product> findAllByProductState(ProductState productState) {
        return productRepository.findByProductState(productState);
    }

    // Método que busca o id para verificar se o produto existe ou não existe.
    private Product findByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    // Método que busca o id para verificar se a categoria existe ou não existe.
    private Category findByIdCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    

}
