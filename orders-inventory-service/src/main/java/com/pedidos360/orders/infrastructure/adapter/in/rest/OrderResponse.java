package com.pedidos360.orders.infrastructure.adapter.in.rest;

import com.pedidos360.orders.domain.model.Order;
import com.pedidos360.orders.domain.model.OrderStatus;

public record OrderResponse(String orderId, String branchId, OrderStatus status) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getOrderId(), order.getBranchId(), order.getStatus());
    }
}