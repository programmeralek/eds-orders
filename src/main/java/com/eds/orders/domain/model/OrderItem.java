package com.eds.orders.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    protected OrderItem() {}

    public OrderItem(String productId, int quantity, BigDecimal price) {
    if(productId == null || productId.isBlank()) {
        throw new IllegalArgumentException("productId is required");
    }
    if(quantity <= 0) {
        throw new IllegalArgumentException("quantity must be greater than 0");
    }
    if(price == null) {
        throw new IllegalArgumentException("price is required");
    }
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public BigDecimal subtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    //ONLY GETTERS - no setters to follow immutability discipline

    public Long getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    //REVIEW
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem that = (OrderItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



}
