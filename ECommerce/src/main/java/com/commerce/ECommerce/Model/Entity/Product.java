package com.commerce.ECommerce.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	private String productName;
	private String description;
	private String category;
	private String subCategory;
	private double price;
	private int stock;
	@ManyToOne
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;
}
