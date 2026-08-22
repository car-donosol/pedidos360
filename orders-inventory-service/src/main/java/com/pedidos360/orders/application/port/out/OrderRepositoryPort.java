package com.pedidos360.orders.application.port.out;

import com.pedidos360.orders.domain.model.Order;

public interface OrderRepositoryPort {
    Order save(Order order);
}