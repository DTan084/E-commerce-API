package com.ecommerce.api.controller;

import com.ecommerce.api.constant.ApiConstants;
import com.ecommerce.api.dto.response.ApiResponse;
import com.ecommerce.api.dto.response.OrderResponse;
import com.ecommerce.api.model.enums.OrderStatus;
import com.ecommerce.api.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderResponse>> checkout(Authentication authentication) {
        OrderResponse order = orderService.checkout(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(order, ApiConstants.ORDER_PLACED));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrderHistory(Authentication authentication) {
        List<OrderResponse> orders = orderService.getOrderHistory(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(orders, ApiConstants.ORDER_HISTORY_RETRIEVED));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        
        OrderResponse order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(order, ApiConstants.ORDER_STATUS_UPDATED));
    }
}
