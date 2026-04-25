package com.ecommerce.api.mapper;

import com.ecommerce.api.dto.response.OrderResponse;
import com.ecommerce.api.entity.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        if (order == null) return null;

        return new OrderResponse(
            order.getId(),
            order.getCreatedAt(),
            order.getTotalAmount(),
            order.getStatus(),
            order.getItems().stream().map(item ->
                new OrderResponse.OrderItemResponse(
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getPriceAtTime()
                )
            ).collect(Collectors.toList())
        );
    }
}
