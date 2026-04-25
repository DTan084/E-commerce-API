package com.ecommerce.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    public CartItem() {}

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends BaseBuilder<CartItem, Builder> {
        public Builder() { entity = new CartItem(); }
        @Override protected Builder self() { return this; }
        public Builder cart(Cart cart) { entity.setCart(cart); return this; }
        public Builder product(Product product) { entity.setProduct(product); return this; }
        public Builder quantity(Integer quantity) { entity.setQuantity(quantity); return this; }
    }
}
