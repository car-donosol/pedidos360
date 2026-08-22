package com.pedidos360.orders.domain.model;

public class OrderItem {
    private final String productId;
    private final Integer quantity;
    private final Double unitPrice;

    public OrderItem(String productId, Integer quantity, Double unitPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad del ítem debe ser mayor a cero");
        }
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public Double getUnitPrice() { return unitPrice; }
}