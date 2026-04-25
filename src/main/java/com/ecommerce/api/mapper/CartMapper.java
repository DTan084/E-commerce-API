package com.ecommerce.api.mapper;

import com.ecommerce.api.dto.response.CartResponse;
import com.ecommerce.api.entity.Cart;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponse toResponse(Cart cart) {
        if (cart == null) return null;

        BigDecimal totalPrice = cart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(
            cart.getId(),
            cart.getItems().stream().map(item ->
                new CartResponse.CartItemResponse(
                    item.getId(),
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getProduct().getPrice(),
                    item.getQuantity(),
                    item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                )
            ).collect(Collectors.toList()),
            totalPrice
        );
    }
}
