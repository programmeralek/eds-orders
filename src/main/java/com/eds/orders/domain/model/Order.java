package com.eds.orders.domain.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id", nullable = false)
    List<OrderItem> items = new ArrayList<>();

    protected Order() {}

    private Order(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("order must contain at least one item");
        }

        this.items.addAll(items);
        this.totalAmount = calculateTotal(items);
        this.status = OrderStatus.CREATED;
        this.createdAt = Instant.now();
    }

    public static Order create(List<OrderItem> items) {
        return new Order(items);
    }

    private BigDecimal calculateTotal(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void markInventoryReserved() {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException(
                    "Cannot reserve inventory for order in status: " + this.status
            );
        }
        this.status = OrderStatus.INVENTORY_RESERVED;
    }

    public void markInventoryRejected() {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException(
                    "Cannot reject inventory for order in status: " + this.status
            );
        }
        this.status = OrderStatus.INVENTORY_REJECTED;
    }

    public void markPaymentSucceeded() {
        if (this.status != OrderStatus.INVENTORY_RESERVED) {
            throw new IllegalStateException(
                    "Payment can only succeed for INVENTORY_RESERVED orders. Current status: " + this.status
            );
        }
        this.status = OrderStatus.PAID;
    }

    public void markPaymentFailed(String reason) {
        if (this.status != OrderStatus.INVENTORY_RESERVED) {
            throw new IllegalStateException(
                    "Payment can only fail for INVENTORY_RESERVED orders. Current status: " + this.status
            );
        }
        this.status = OrderStatus.PAYMENT_FAILED;
    }
    // Read-only accessors

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

}
