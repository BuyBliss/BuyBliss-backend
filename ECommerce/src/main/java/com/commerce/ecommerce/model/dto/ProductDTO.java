package com.commerce.ecommerce.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private String description;
    private String category;
    private String subCategory;
    private double price;
    private int stock;
    private VendorDTO vendor;
}
