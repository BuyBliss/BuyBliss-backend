package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.entity.Cart;
import com.commerce.ecommerce.model.entity.CartItem;
import com.commerce.ecommerce.model.request.UpdateCartUIRequest;
import com.commerce.ecommerce.repositoy.CartItemRepo;
import com.commerce.ecommerce.repositoy.CartRepository;
import com.commerce.ecommerce.repositoy.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepo cartItemRepo;

    @Autowired
    ProductRepo productRepo;

    public void addToCart(UpdateCartUIRequest addRequest) {
        Cart cart = cartRepository.getReferenceById(addRequest.getCartId());

        CartItem cartItem = cart.getCartItemList().stream()
                .filter(cartI -> cartI.getProduct().getProductId().equals(addRequest.getProductId()))
                .findAny().orElse(new CartItem());

        if (cartItem.getCartItemId() != null) {
            cartItem.setProductQuantity(cartItem.getProductQuantity() + 1);

        } else {
            cartItem.setProduct(productRepo.getReferenceById(addRequest.getProductId()));
            cartItem.setProductQuantity(1);
            cart.getCartItemList().add(cartItem);
        }
        cart.setSize(cart.getSize() + 1);
        cartItem.setCart(cart);
        cartItemRepo.save(cartItem);

    }

    //@Transactional        (backup to try if it fails)
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.getReferenceById(cartId);
        emptyCart(cart);
    }

    public void removeFromCart(UpdateCartUIRequest removeRequest) {

        Cart cart = cartRepository.getReferenceById(removeRequest.getCartId());

        if (cart.getSize() <= 1) {
            cart.getCartItemList().clear();
            cart.setSize(0);
            cartRepository.save(cart);
            return;
        } else {
            cart.setSize(cart.getSize() - 1);
            cartRepository.save(cart);
        }

        CartItem cartItem = cart.getCartItemList().stream()
                .filter(cartI -> cartI.getProduct().getProductId().equals(removeRequest.getProductId()))
                .findFirst().orElse(null);

        //if(cartItem != null)
        cartItem.setProductQuantity(cartItem.getProductQuantity() - 1);

        if (cartItem.getProductQuantity() > 0)
            cartItemRepo.save(cartItem);
        else
            cartItemRepo.deleteById(cartItem.getCartItemId());
    }

    public void emptyCart(Cart cart) {
        cart.getCartItemList().clear();
        cart.setSize(0);
        cartRepository.save(cart);
    }
}
