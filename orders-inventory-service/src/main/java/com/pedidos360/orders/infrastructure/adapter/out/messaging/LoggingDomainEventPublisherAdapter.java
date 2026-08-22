package com.pedidos360.orders.infrastructure.adapter.out.messaging;

import com.pedidos360.orders.application.port.out.DomainEventPublisherPort;
import com.pedidos360.orders.domain.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingDomainEventPublisherAdapter implements DomainEventPublisherPort {

    private static final Logger log = LoggerFactory.getLogger(LoggingDomainEventPublisherAdapter.class);

    @Override
    public void publishOrderCreatedEvent(Order order) {
        log.info("[EVENTO] Pedido creado: {} (sucursal={}, estado={})",
                order.getOrderId(), order.getBranchId(), order.getStatus());
    }
}