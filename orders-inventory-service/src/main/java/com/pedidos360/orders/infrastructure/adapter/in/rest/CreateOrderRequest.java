package com.pedidos360.orders.infrastructure.adapter.in.rest;

import com.pedidos360.orders.domain.model.DispatchType;

public record CreateOrderRequest(
    String branchId,
    String productId,
    Integer quantity,
    DispatchType dispatchType
) {}