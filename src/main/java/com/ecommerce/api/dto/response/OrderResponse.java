package com.ecommerce.api.dto.response;

import com.ecommerce.api.model.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemResponse> items;

    public OrderResponse() {}

    public OrderResponse(Long id, LocalDateTime orderDate, BigDecimal totalAmount, OrderStatus status, List<OrderItemResponse> items) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.items = items;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public List<OrderItemResponse> getItems() { return items; }
    public void setItems(List<OrderItemResponse> items) { this.items = items; }

    public static class OrderItemResponse {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal priceAtTime;

        public OrderItemResponse() {}

        public OrderItemResponse(Long productId, String productName, Integer quantity, BigDecimal priceAtTime) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.priceAtTime = priceAtTime;
        }

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public BigDecimal getPriceAtTime() { return priceAtTime; }
        public void setPriceAtTime(BigDecimal priceAtTime) { this.priceAtTime = priceAtTime; }
    }
}
