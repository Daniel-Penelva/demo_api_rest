package com.project.demo_api_rest.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePriceAndQuantityDTO {
    
    private BigDecimal price;
    private int quantity;
}
