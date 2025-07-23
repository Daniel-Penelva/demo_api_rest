package com.project.demo_api_rest.service;

import java.math.BigDecimal;
import java.util.List;

import com.project.demo_api_rest.enums.ProductState;
import com.project.demo_api_rest.model.Product;

public interface ProductService {

    Product addProduct(Product product);

    Product associateCategoryInProduct(Long categoryId, Product product);

    List<Product> findAllProducts();

    Product findByNameProduct(String name);

    Product findByIdProduct(Long id);

    Product updateProduct(Long id, Product product);

    Product updatePriceProduct(Long id, BigDecimal price);

    Product updatePriceAndQuantityProduct(Long id, BigDecimal price, int quantity);

    void deleteProduct(Long id);

    Product modifyProductState(Long id, ProductState productState);

    List<Product> findAllByProductState(ProductState productState);

}
