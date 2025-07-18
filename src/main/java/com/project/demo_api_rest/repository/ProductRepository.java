package com.project.demo_api_rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo_api_rest.enums.ProductState;
import com.project.demo_api_rest.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Optional<Product> findByProductState(ProductState productState);
}
