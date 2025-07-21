package com.project.demo_api_rest.model;

import java.math.BigDecimal;

import com.project.demo_api_rest.enums.ProductState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal price;

    @Column(nullable = false)
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private int quantity;

    @NotNull(message = "O estado do produto é obrigatório")
    @Enumerated(EnumType.STRING)  // @EnumType - Define o tipo de enumeração. No caso EnumType.String define que o enum será armazenado como string.
    @Column(name = "product_state", nullable = false)
    private ProductState productState;

    // Muitos produtos podem ter apenas uma categoria
    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id")
    private Category category;

}
