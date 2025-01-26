package com.commerce.ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vendor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vendorId;
	@NotEmpty(message = "Name cannot be empty")
	@Size(min = 3, message = "Name at least have 3 letters")
	private String name;
	@Email(message = "Invalid email address")
	private String email;
	@Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number")
	private String contactNo;
	@OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
	private List<Product> products;
}
