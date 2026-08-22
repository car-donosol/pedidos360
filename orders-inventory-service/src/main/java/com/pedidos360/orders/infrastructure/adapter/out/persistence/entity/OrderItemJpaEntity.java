package com.pedidos360.orders.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderItemJpaEntity {
    private String productId;
    private Integer quantity;
    private Double unitPrice;

    protected OrderItemJpaEntity() {}

    public OrderItemJpaEntity(String productId, Integer quantity, Double unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public Double getUnitPrice() { return unitPrice; }
}