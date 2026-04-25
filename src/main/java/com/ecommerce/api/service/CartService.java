package com.ecommerce.api.service;

import com.ecommerce.api.dto.request.CartItemRequest;
import com.ecommerce.api.dto.response.CartResponse;

public interface CartService {
    CartResponse addToCart(String userEmail, CartItemRequest request);
    CartResponse getCart(String userEmail);
    CartResponse removeProductFromCart(String userEmail, Long productId);
}
