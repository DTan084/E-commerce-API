package com.ecommerce.api.service;

import com.ecommerce.api.dto.response.OrderResponse;
import com.ecommerce.api.model.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponse checkout(String userEmail);
    List<OrderResponse> getOrderHistory(String userEmail);
    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
}
