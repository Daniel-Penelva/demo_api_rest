package com.project.demo_api_rest.model;

import com.project.demo_api_rest.enums.ProductState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT_TBL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 35)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)  // @EnumType - Define o tipo de enumeração. No caso EnumType.String define que o enum será armazenado como string.
    @Column(name = "product_state", nullable = false)
    private ProductState productState;

}
