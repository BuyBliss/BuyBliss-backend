package com.commerce.ECommerce.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MyCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private List<Long> listOfProductId;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<Long> getListOfProductId() {
        return listOfProductId;
    }

    public void setListOfProductId(List<Long> listOfProductId) {
        this.listOfProductId = listOfProductId;
    }

    @Override
    public String toString() {
        return "MyCart{" +
                "cartId=" + cartId +
                ", listOfProductId=" + listOfProductId +
                '}';
    }
}
