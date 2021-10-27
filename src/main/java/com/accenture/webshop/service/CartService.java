package com.accenture.webshop.service;

import com.accenture.webshop.model.CartItem;

import java.util.Optional;

public interface CartService {

    Optional<CartItem> getCartById(Long id);

    CartItem saveCart(CartItem cartItem);
}
