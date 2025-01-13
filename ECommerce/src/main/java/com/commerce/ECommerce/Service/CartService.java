package com.commerce.ECommerce.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commerce.ECommerce.Model.Cart;
import com.commerce.ECommerce.Model.Consumer;
import com.commerce.ECommerce.Model.Product;
import com.commerce.ECommerce.Repositoy.CartRepository;
import com.commerce.ECommerce.Repositoy.ConsumerRepo;
import com.commerce.ECommerce.Repositoy.ProductRepo;

@Service
public class CartService {
	
	@Autowired
	 CartRepository cartRepository;
	
    @Autowired
    ConsumerRepo consumerRepo;

    @Autowired
    ProductRepo productRepo;
	
	public void addTocart(Consumer customer, Product product,int quantity)
	{
        Cart cart = customer.getCart();
        cart.setProduct(product);
        cart.setQuantity(quantity);
        cart.setCustomer(customer);
        cartRepository.save(cart);
    }
	
	public void deleteCart(Cart cart)
	{
		cartRepository.delete(cart);
	}
}
