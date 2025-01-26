package com.commerce.ecommerce.model.response;

import com.commerce.ecommerce.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResponse {
    private int totalPages;
    private List<ProductDTO> productList;
}
