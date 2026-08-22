package com.pedidos360.orders.application.port.in;

import com.pedidos360.orders.domain.model.Order;

public interface CreateOrderUseCase {
    Order execute(CreateOrderCommand command);
}