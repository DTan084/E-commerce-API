package com.ecommerce.api.entity;

import com.ecommerce.api.model.enums.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order() {}

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends BaseBuilder<Order, Builder> {
        public Builder() { entity = new Order(); }
        @Override protected Builder self() { return this; }
        public Builder user(User user) { entity.setUser(user); return this; }
        public Builder totalAmount(BigDecimal totalAmount) { entity.setTotalAmount(totalAmount); return this; }
        public Builder status(OrderStatus status) { entity.setStatus(status); return this; }
    }
}
