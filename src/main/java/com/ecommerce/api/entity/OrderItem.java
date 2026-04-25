package com.ecommerce.api.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtTime;

    public OrderItem() {}

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPriceAtTime() { return priceAtTime; }
    public void setPriceAtTime(BigDecimal priceAtTime) { this.priceAtTime = priceAtTime; }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends BaseBuilder<OrderItem, Builder> {
        public Builder() { entity = new OrderItem(); }
        @Override protected Builder self() { return this; }
        public Builder order(Order order) { entity.setOrder(order); return this; }
        public Builder product(Product product) { entity.setProduct(product); return this; }
        public Builder quantity(Integer quantity) { entity.setQuantity(quantity); return this; }
        public Builder priceAtTime(BigDecimal priceAtTime) { entity.setPriceAtTime(priceAtTime); return this; }
    }
}
