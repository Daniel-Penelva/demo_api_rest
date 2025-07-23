package com.project.demo_api_rest.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.demo_api_rest.enums.ProductState;
import com.project.demo_api_rest.exception.ProductNotFoundException;
import com.project.demo_api_rest.model.Product;
import com.project.demo_api_rest.repository.ProductRepository;
import com.project.demo_api_rest.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImplements implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        System.out.println("Produto sendo salvo na service: " + product); // Adicione este log
        return productRepository.save(product);
    }

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
        productBD.setCategory(product.getCategory());

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

}
