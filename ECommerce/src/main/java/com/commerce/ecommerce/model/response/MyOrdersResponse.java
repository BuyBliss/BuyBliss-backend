package com.commerce.ecommerce.model.response;

import com.commerce.ecommerce.model.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOrdersResponse {
    private int totalPages;
    private List<OrderDTO> orderList;
}
