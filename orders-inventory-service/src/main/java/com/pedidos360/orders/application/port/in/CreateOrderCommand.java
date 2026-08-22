package com.pedidos360.orders.application.port.in;

import com.pedidos360.orders.domain.model.DispatchType;

// Usamos Record (Java 14+) para inmutabilidad perfecta
public record CreateOrderCommand(
    String customerId,
    String branchId,
    String productId,
    Integer quantity,
    DispatchType dispatchType
) {}