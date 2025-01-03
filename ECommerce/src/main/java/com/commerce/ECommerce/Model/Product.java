package com.commerce.ECommerce.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    private Integer productId;
    private String productName;
    private String description;
    private String category;
    private String subCategory;
    private double price;
    private int stock;
    private Integer vendorId;
}
