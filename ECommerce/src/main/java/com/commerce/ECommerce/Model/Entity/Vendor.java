package com.commerce.ECommerce.Model.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Vendor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vendorId;
	private String name;
	private String email;
	private String contactNo;
	@OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
	private List<Product> products;

}
